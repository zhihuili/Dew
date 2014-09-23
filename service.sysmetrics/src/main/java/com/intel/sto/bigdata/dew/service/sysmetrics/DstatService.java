package com.intel.sto.bigdata.dew.service.sysmetrics;

import com.intel.sto.bigdata.dew.service.Service;

public class DstatService extends Service {

  private boolean run = true;

  @Override
  public void run() {
    DstatProcessor dp = new DstatProcessor();
    dp.startThread();
    while (run) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    dp.destroy();
  }

  @Override
  public void stop() {
    run = false;
  }

}
