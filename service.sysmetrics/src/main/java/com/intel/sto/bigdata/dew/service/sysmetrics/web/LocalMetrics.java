package com.intel.sto.bigdata.dew.service.sysmetrics.web;

import com.intel.sto.bigdata.dew.service.sysmetrics.app.CircleLink;

public class LocalMetrics {
  private LocalCollectCurrentDstatThread thread;

  public void startCollectCulsterDstat(CircleLink cl, int interval) {
    thread = new LocalCollectCurrentDstatThread(cl, interval);
    thread.start();
  }

  public void stop() {
    thread.stopCllect();
  }
}
