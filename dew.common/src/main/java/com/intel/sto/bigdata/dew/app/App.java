package com.intel.sto.bigdata.dew.app;

import static akka.pattern.Patterns.ask;
import static java.util.concurrent.TimeUnit.SECONDS;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import akka.actor.ActorIdentity;
import akka.actor.ActorRef;
import akka.actor.Identify;
import akka.actor.Props;
import akka.actor.ReceiveTimeout;
import akka.actor.UntypedActor;

import com.intel.sto.bigdata.dew.message.Agents;
import com.intel.sto.bigdata.dew.message.ServiceComplete;
import com.intel.sto.bigdata.dew.message.ServiceRequest;
import com.intel.sto.bigdata.dew.message.ServiceResponse;

public class App extends UntypedActor {

  String[] agentUrls;
  String url;
  ActorRef actor;
  ActorRef listener;
  FiniteDuration duration = Duration.create(3, SECONDS);
  int resultNum = 0;

  public App(String url, Class<AppListener> listener) {
    this.url = url;
    this.listener = getContext().actorOf(Props.create(listener), "listener");
    sendIdentifyRequest();
    init();
  }

  private void sendIdentifyRequest() {
    try {
      actor =
          ((ActorIdentity) ask(getContext().actorSelection(url), new Identify(url), 3000).result(
              duration, null)).getRef();
      getContext().watch(actor);
    } catch (Exception e) {
      e.printStackTrace();
    }
    getContext()
        .system()
        .scheduler()
        .scheduleOnce(duration, getSelf(), ReceiveTimeout.getInstance(), getContext().dispatcher(),
            getSelf());
  }

  private void init() {
    try {
      Agents agents = (Agents) ask(actor, new Agents(), 3000).result(duration, null);
      agentUrls = agents.getResponseUrls().split(";");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onReceive(Object message) throws Exception {

    if (message instanceof ServiceRequest) {
      for (String agentUrl : agentUrls) {
        getContext().actorSelection(agentUrl).tell(message, getSelf());
      }
    }
    if (message instanceof ServiceResponse) {
      listener.tell(message, null);
      if (resultNum++ >= agentUrls.length) {
        listener.tell(new ServiceComplete(), null);
      }
    }
  }

}
