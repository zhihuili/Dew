package com.intel.sto.bigdata.dew.service.sysmetrics;

import java.io.BufferedInputStream;
import java.io.File;
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
      String tmpFilePath = "/tmp/dew.dstat";
      File tmpFile = new File(tmpFilePath);
      if (tmpFile.exists()) {
        tmpFile.delete();
      }
      tmpFile.createNewFile();
      String[] cmd =
          {
              "/bin/sh",
              "-c",
              "dstat --mem --io --cpu --net -N eth0,eth1,total --disk --output " + tmpFilePath
                  + " | tail -f " + tmpFilePath };
      Process process = Runtime.getRuntime().exec(cmd);
      InputStreamReader isr =
          new InputStreamReader(new BufferedInputStream(process.getInputStream()));
      int c;
      StringBuilder sb = new StringBuilder();
      int rowNum = 0;
      while ((c = isr.read()) != -1 && go) {
        if (c != System.getProperty("line.separator").charAt(0)) {
          sb.append((char) c);
        } else {
          if (rowNum++ < 7) {
            sb = new StringBuilder();
            continue;
          }
          sb.append("|" + String.valueOf(System.currentTimeMillis()));
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
    long firstTime = Long.valueOf(first.split("\\|")[1]);
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
      sb.append(stats.get(i).split("\\|")[0] + ";");
    }
    sb.append(stats.get((int) (skip + duration)).split("\\|")[0]);
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
    Thread.sleep(5000);
    System.out.println(dp.getStats());
    dp.kill();
  }

}
