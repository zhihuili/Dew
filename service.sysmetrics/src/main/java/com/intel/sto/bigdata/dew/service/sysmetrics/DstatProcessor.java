package com.intel.sto.bigdata.dew.service.sysmetrics;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DstatProcessor extends Thread {
  private List<String> stats = new ArrayList<String>();

  @Override
  public void run() {
    try {
      Process process = Runtime.getRuntime().exec("dstat -c -d -m -n -t");
      InputStreamReader isr = new InputStreamReader(process.getInputStream());
      int c;
      StringBuilder sb = new StringBuilder();
      int rowNum = 0;
      while ((c = isr.read()) != -1) {
        if (c != '\n') {
          sb.append((char) c);
        } else {
          if (rowNum++ < 3) {
            sb = new StringBuilder();
            continue;
          }
          System.out.println(sb);
          stats.add(sb.toString());
          sb = new StringBuilder();
        }
      }
      isr.close();
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

  /**
   * @param args
   */
  public static void main(String[] args) {

    DstatProcessor dp = new DstatProcessor();
    // dp.setDaemon(true);
    dp.start();
  }

}
