package com.intel.sto.bigdata.dew.service.sysmetrics.web;

import java.util.HashMap;

import com.intel.sto.bigdata.dew.service.sysmetrics.DstatProcessor;
import com.intel.sto.bigdata.dew.service.sysmetrics.app.CircleLink;
import com.intel.sto.bigdata.dew.service.sysmetrics.app.CircleLinkNode;

public class LocalCollectCurrentDstatThread extends Thread {
  private int interval;
  private CircleLink cl;
  private boolean go = true;

  public LocalCollectCurrentDstatThread(CircleLink cl, int interval) {
    this.cl = cl;
    this.interval = interval;
  }

  @Override
  public void run() {
    while (go) {
      DstatProcessor dp = DstatProcessor.getInstance();
      if (dp == null) {
        try {
          Thread.sleep(interval);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        continue; // dp is not ready.
      }
      CircleLinkNode node =
          new CircleLinkNode(new HashMap<String, String>(), System.currentTimeMillis());
      cl.addItem(node);
      node.getElement().put("this", dp.getCurrentDstat());
      try {
        Thread.sleep(interval);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public void stopCllect() {
    go = false;
  }
}
