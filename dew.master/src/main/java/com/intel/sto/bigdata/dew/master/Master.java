package com.intel.sto.bigdata.dew.master;

import java.util.Map.Entry;
import java.util.UUID;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import com.intel.sto.bigdata.dew.message.AgentList;
import com.intel.sto.bigdata.dew.message.AgentRegister;
import com.intel.sto.bigdata.dew.message.StartService;

public class Master extends UntypedActor {
  private LoggingAdapter log = Logging.getLogger(this);

  public Master() {
    ClusterState.initService();
  }

  @Override
  public void onReceive(Object message) throws Exception {
    if (message instanceof AgentRegister) {

      AgentRegister ai = (AgentRegister) message;
      ai.setUrl(getSender().path().toString());
      ClusterState.addAgent(UUID.randomUUID().toString(), ai);
      log.info("Agent registered." + getSender().path());
      for (Entry<String, String> entry : ClusterState.getServiceMap().entrySet()) {
        StartService ss = new StartService(entry.getKey(), entry.getValue());
        getSender().tell(ss, getSelf());
      }
    } else if (message instanceof AgentList) {
      ((AgentList) message).setResponseUrls(ClusterState.buildAgentsString());
      getSender().tell(message, getSelf());
    } else {
      log.info("Unhandle message:" + message.getClass().getName());
      unhandled(message);
    }

  }
}
