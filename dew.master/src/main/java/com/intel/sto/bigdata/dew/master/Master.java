package com.intel.sto.bigdata.dew.master;

import java.util.Map.Entry;

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
      ClusterState.addAgent(ai);
      log.info("Agent registered." + ai.getUrl());
      for (Entry<String, String> entry : ClusterState.getServiceMap().entrySet()) {
        StartService ss = new StartService(entry.getKey(), entry.getValue());
        getSender().tell(ss, getSelf());
      }
    } else if (message instanceof AgentList) {
      AgentList al = (AgentList) message;
      al.setResponseUrls(ClusterState.findAgent(al.getRequestHosts()));
      getSender().tell(al, getSelf());
    } else {
      log.info("Unhandle message:" + message.getClass().getName());
      unhandled(message);
    }

  }
}
