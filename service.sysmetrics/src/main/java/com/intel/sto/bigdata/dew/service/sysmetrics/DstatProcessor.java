package com.intel.sto.bigdata.dew.service.sysmetrics;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.intel.sto.bigdata.dew.exception.ServiceException;

public class DstatProcessor extends Thread {
  private List<String> stats = new ArrayList<String>();
  private boolean go = true;
  private Lock lock = new ReentrantLock();// maybe needn't

  @Override
  public void run() {
    try {
      Process process = Runtime.getRuntime().exec("dstat -c -d -m -n");
      InputStreamReader isr =
          new InputStreamReader(new BufferedInputStream(process.getInputStream()));
      int c;
      StringBuilder sb = new StringBuilder();
      int rowNum = 0;
      while ((c = isr.read()) != -1 && go) {
        if (c != '\n') {
          sb.append((char) c);
        } else {
          if (rowNum++ < 3) {
            sb = new StringBuilder();
            continue;
          }
          sb.append("|" + String.valueOf(System.currentTimeMillis()));
          System.out.println(sb);
          lock.lock();
          stats.add(sb.toString());
          lock.unlock();
          sb = new StringBuilder();
        }
      }
      isr.close();
      process.destroy();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void startThread() {
    this.setDaemon(true);
    this.start();
  }

  public List<String> getStats() {
    return stats;
  }

  public void setStats(List<String> stats) {
    this.stats = stats;
  }

  public String findWorkloadMetrics(long startTime, long endTime) throws ServiceException {
    lock.lock();
    if (stats.isEmpty()) {
      throw new ServiceException("No dstat data!");
    }
    String first = stats.get(0);
    long firstTime = Long.valueOf(first.split("|")[4]);
    if (firstTime > startTime) {
      throw new ServiceException("Service start time is later than your query time about"
          + (firstTime - startTime) / 1000 + "s");
    }
    long skip = (startTime - firstTime) / 1000;
    long duration = (endTime - startTime) / 1000;
    if (stats.size() <= (skip + duration)) {
      throw new ServiceException("No enough dstat data!");
    }
    lock.unlock();
    StringBuilder sb = new StringBuilder();
    for (int i = (int) skip; i < skip + duration; i++) {
      sb.append(stats.get(i) + ";");
    }
    sb.append(stats.get((int) (skip + duration)));
    return sb.toString();
  }

  public void kill() {
    go = false;
  }

  /**
   * @param args
   * @throws InterruptedException
   */
  public static void main(String[] args) throws InterruptedException {

    DstatProcessor dp = new DstatProcessor();
    dp.startThread();
    Thread.sleep(10000);
    dp.kill();
  }

}
