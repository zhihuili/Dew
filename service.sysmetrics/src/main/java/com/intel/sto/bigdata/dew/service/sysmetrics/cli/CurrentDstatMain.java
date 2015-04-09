package com.intel.sto.bigdata.dew.service.sysmetrics.cli;

import org.apache.log4j.Logger;

import com.intel.sto.bigdata.dew.service.sysmetrics.app.CircleLink;
import com.intel.sto.bigdata.dew.service.sysmetrics.app.GroupMetricsApp;

public class CurrentDstatMain {
  private static GroupMetricsApp dstatApp;
  private static final int DSTAT_INTERVAL = 2000;
  private static Logger log = Logger.getLogger(CurrentDstatMain.class);

  public static void main(String[] args) throws Exception {
    try {
      CircleLink cl = CircleLink.getInstance();
      dstatApp = GroupMetricsApp.getInstance();
      dstatApp.startCollectCulsterDstat(cl, DSTAT_INTERVAL);
      PrintThread pt = new PrintThread(cl);
      pt.start();

    } catch (Exception e) {
      log.error(e.getMessage());
    }

  }

  private static class PrintThread extends Thread {
    CircleLink cl;

    public PrintThread(CircleLink cl) {
      this.cl = cl;
    }

    @Override
    public void run() {
      while (true) {
        try {
          Thread.sleep(DSTAT_INTERVAL);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        System.out.println(cl.buildList());
      }
    }
  }
}
