package com.intel.sto.bigdata.dew.agent;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class SampleService extends UntypedActor {
  private ActorRef master;

  public SampleService(ActorRef master) {
    this.master = master;
  }

  @Override
  public void onReceive(Object arg0) throws Exception {
    if (arg0 instanceof String) {
      master.tell(arg0, getSelf());
    }
    System.out.println(arg0 + "---" + getSender().path());

  }

}
