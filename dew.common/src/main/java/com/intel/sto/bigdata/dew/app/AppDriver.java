package com.intel.sto.bigdata.dew.app;

import static akka.pattern.Patterns.ask;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.HashSet;
import java.util.Set;

import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import akka.actor.ActorIdentity;
import akka.actor.ActorRef;
import akka.actor.Identify;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import com.intel.sto.bigdata.dew.message.AgentList;
import com.intel.sto.bigdata.dew.message.AgentRegister;
import com.intel.sto.bigdata.dew.message.ProcessCompletion;
import com.intel.sto.bigdata.dew.message.ServiceCompletion;
import com.intel.sto.bigdata.dew.message.ServiceRequest;
import com.intel.sto.bigdata.dew.message.ServiceResponse;
import com.intel.sto.bigdata.dew.message.ServiceTimeout;

public class AppDriver extends UntypedActor {

  private LoggingAdapter log = Logging.getLogger(this);

  Set<AgentRegister> agents;
  String url;
  ActorRef master;
  ActorRef listener;
  FiniteDuration duration = Duration.create(3, SECONDS);
  int resultNum = 0;
  AppDes appDes;
  ActorRef proxy;
  Set<ActorRef> agentActors = new HashSet<ActorRef>();

  public AppDriver(String url, AppProcessor processor, AppDes appDes) {
    this.url = url;
    this.appDes = appDes;
    this.listener = getContext().actorOf(Props.create(AppListener.class, processor), "listener");
    sendIdentifyRequest();
    init();
  }

  private void sendIdentifyRequest() {
    try {
      master =
          ((ActorIdentity) ask(getContext().actorSelection(url), new Identify(url), 3000).result(
              duration, null)).getRef();
      // getContext().watch(master);
    } catch (Throwable e) {
      log.error("Can't connect dew master:" + url);
      throw new RuntimeException(e);
    }
    // getContext()
    // .system()
    // .scheduler()
    // .scheduleOnce(duration, getSelf(), ReceiveTimeout.getInstance(), getContext().dispatcher(),
    // getSelf());
  }

  private void init() {
    try {
      AgentList al = new AgentList();
      al.setRequestHosts(appDes.getHosts());
      AgentList agentList = (AgentList) ask(master, al, 3000).result(duration, null);
      agents = agentList.getResponseUrls();
      log.debug("=========target agent=========" + agents.toString());
      for (AgentRegister agent : agents) {
        try {
          ActorRef actorRef =
              ((ActorIdentity) ask(getContext().actorSelection(agent.getUrl()),
                  new Identify(agent.getUrl()), 3000).result(duration, null)).getRef();
          if (actorRef != null) {
            agentActors.add(actorRef);
          }
        } catch (Throwable e) {
          log.error("Can't connect agent:" + agent.getUrl());
        }
      }
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onReceive(Object message) throws Exception {
    log.debug("=========app receive=========" + message);
    if (message instanceof ServiceRequest) {
      proxy = getSender();
      for (ActorRef agent : agentActors) {
        agent.tell(message, getSelf());
      }
    } else if (message instanceof ServiceResponse) {
      ServiceResponse response = ((ServiceResponse) message);
      if (!response.hasException()) {
        listener.tell(message, getSelf());
      } else {
        log.error("service error:" + response.getEm().getError() + " from:" + getSender());
      }
      if (++resultNum >= agentActors.size()) {
        getSelf().tell(new ServiceCompletion(), getSelf());
      }
    } else if (message instanceof ServiceCompletion) {
      listener.tell(message, getSelf());
    } else if (message instanceof ServiceTimeout) {
      listener.tell(new ServiceCompletion(), getSelf());
      proxy = getSender();
    } else if (message instanceof ProcessCompletion) {
      proxy.tell(message, getSelf());
    } else {
      log.error("unhandled message:" + message);
    }
  }

}
