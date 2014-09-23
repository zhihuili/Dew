package com.intel.sto.bigdata.dew.app;

import static akka.pattern.Patterns.ask;
import static java.util.concurrent.TimeUnit.SECONDS;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import akka.actor.ActorIdentity;
import akka.actor.ActorRef;
import akka.actor.Identify;
import akka.actor.ReceiveTimeout;
import akka.actor.UntypedActor;

import com.intel.sto.bigdata.dew.message.Agents;

public class App extends UntypedActor {

  String[] agentUrls;
  String url;
  ActorRef actor;
  FiniteDuration d = Duration.create(3, SECONDS);

  public App(String url) {
    this.url = url;
    sendIdentifyRequest();
    init();
  }

  private void sendIdentifyRequest() {
    try {
      actor =
          ((ActorIdentity) ask(getContext().actorSelection(url), new Identify(url), 3000).result(d,
              null)).getRef();
      getContext().watch(actor);
    } catch (Exception e) {
      e.printStackTrace();
    }
    getContext()
        .system()
        .scheduler()
        .scheduleOnce(d, getSelf(), ReceiveTimeout.getInstance(), getContext().dispatcher(),
            getSelf());
  }

  private void init() {
    try {
      Agents agents = (Agents) ask(actor, new Agents(), 3000).result(d, null);
      agentUrls = agents.getResponseUrls().split(";");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onReceive(Object arg0) throws Exception {
    // TODO Auto-generated method stub

  }

}
