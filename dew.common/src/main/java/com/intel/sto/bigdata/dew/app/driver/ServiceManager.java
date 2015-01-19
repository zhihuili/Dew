package com.intel.sto.bigdata.dew.app.driver;

import static akka.pattern.Patterns.ask;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
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

import com.intel.sto.bigdata.dew.app.AppDes;
import com.intel.sto.bigdata.dew.app.AppListener;
import com.intel.sto.bigdata.dew.app.AppProcessor;
import com.intel.sto.bigdata.dew.message.AgentInfo;
import com.intel.sto.bigdata.dew.message.AgentQuery;
import com.intel.sto.bigdata.dew.message.AgentRegister;
import com.intel.sto.bigdata.dew.message.ProcessCompletion;
import com.intel.sto.bigdata.dew.message.ServiceCompletion;
import com.intel.sto.bigdata.dew.message.ServiceRequest;
import com.intel.sto.bigdata.dew.message.ServiceResponse;
import com.intel.sto.bigdata.dew.message.ServiceTimeout;

public class ServiceManager extends UntypedActor {
  private ActorRef listener;
  private ActorRef master;
  private AppDes appDes;
  private Set<AgentRegister> agents;
  private Map<AgentRegister, ActorRef> agentActors = new HashMap<AgentRegister, ActorRef>();
  private ActorRef driver;
  private int resultNum = 0;
  private FiniteDuration duration = Duration.create(3, SECONDS);
  private LoggingAdapter log = Logging.getLogger(this);

  public ServiceManager(ActorRef master, AppProcessor processor, AppDes appDes) {
    this.master = master;
    this.appDes = appDes;
    this.listener = getContext().actorOf(Props.create(AppListener.class, processor), "listener");
    init();
  }

  private void init() {
    try {
      AgentQuery al = new AgentQuery();
      al.setRequestHosts(appDes.getHosts());
      al.setServiceName(appDes.getServiceName());
      AgentQuery agentList = (AgentQuery) ask(master, al, 3000).result(duration, null);
      agents = agentList.getResponseUrls();
      log.debug("=========target agent=========" + agents.toString());
      for (AgentRegister agent : agents) {
        try {
          ActorRef actorRef =
              ((ActorIdentity) ask(getContext().actorSelection(agent.getUrl()),
                  new Identify(agent.getUrl()), 3000).result(duration, null)).getRef();
          if (actorRef != null) {
            agentActors.put(agent, actorRef);
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
    log.debug("=========app received=========" + message);
    if (message instanceof AgentInfo) {
      ((AgentInfo) message).setAgentActors(agentActors);
      getSender().tell(message, getSelf());
    } else if (message instanceof ServiceRequest) {
      driver = getSender();
      for (Entry<AgentRegister, ActorRef> agent : agentActors.entrySet()) {
        agent.getValue().tell(message, getSelf());
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
      driver = getSender();
    } else if (message instanceof ProcessCompletion) {
      driver.tell(message, getSelf());
    } else {
      log.error("unhandled message:" + message);
    }

  }

}
