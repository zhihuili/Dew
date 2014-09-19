package com.intel.sto.bigdata.dew.agent;

import static java.util.concurrent.TimeUnit.SECONDS;
import scala.concurrent.duration.Duration;
import akka.actor.ActorIdentity;
import akka.actor.ActorRef;
import akka.actor.Identify;
import akka.actor.Props;
import akka.actor.ReceiveTimeout;
import akka.actor.UntypedActor;
import akka.japi.Procedure;

import com.intel.sto.bigdata.dew.message.AgentInfo;

public class Agent extends UntypedActor {
  String masterUrl;
  ActorRef master;

  public Agent(String masterUrl) {
    this.masterUrl = masterUrl;
    sendIdentifyRequest();
  }

  private void sendIdentifyRequest() {
    System.out.println(masterUrl);
    getContext().actorSelection(masterUrl).tell(new Identify(masterUrl), getSelf());
    getContext()
        .system()
        .scheduler()
        .scheduleOnce(Duration.create(3, SECONDS), getSelf(), ReceiveTimeout.getInstance(),
            getContext().dispatcher(), getSelf());
  }

  @Override
  public void onReceive(Object message) throws Exception {
    if (message instanceof ActorIdentity) {
      master = ((ActorIdentity) message).getRef();
      if (master == null) {
        System.err.println("Master not available: " + masterUrl);
      } else {
        getContext().watch(master);
        getContext().become(active, true);
        master.tell(new AgentInfo(), getSelf());
        ActorRef sample = getContext().actorOf(Props.create(SampleService.class, master), "sample");
        sample.tell("OK", getSelf());
        getContext().stop(sample);
        sample.tell("aa", null);
      }
    } else if (message instanceof ReceiveTimeout) {
      sendIdentifyRequest();
    } else {
      System.out.println("Master not ready yet");
    }
  }

  Procedure<Object> active = new Procedure<Object>() {
    @Override
    public void apply(Object message) {
      
    }
  };
}
