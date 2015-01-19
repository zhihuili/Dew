package com.intel.sto.bigdata.dew.service.sysmetrics.app;

import akka.actor.ActorRef;

import com.intel.sto.bigdata.dew.service.sysmetrics.message.CurrentDstatServiceRequest;

public class CollectionCurrentDstatThread extends Thread {
  private ActorRef groupActor;
  private int interval;
  private boolean go = true;

  public CollectionCurrentDstatThread(ActorRef groupActor, int interval) {
    this.groupActor = groupActor;
    this.interval = interval;
  }

  @Override
  public void run() {
    while (go) {
      groupActor.tell(new CurrentDstatServiceRequest(), null);
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
