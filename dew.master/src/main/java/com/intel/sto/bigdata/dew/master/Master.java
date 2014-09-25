package com.intel.sto.bigdata.dew.master;

import java.util.UUID;

import akka.actor.UntypedActor;

import com.intel.sto.bigdata.dew.message.AgentRegister;
import com.intel.sto.bigdata.dew.message.AgentList;

public class Master extends UntypedActor {
  @Override
  public void onReceive(Object message) throws Exception {
    if (message instanceof AgentRegister) {

      AgentRegister ai = (AgentRegister) message;
      ai.setUrl(getSender().path().toString());
      ClusterState.addAgent(UUID.randomUUID().toString(), ai);

      System.out.println("Agent registered." + getSender().path());
    } else if (message instanceof AgentList) {
      ((AgentList) message).setResponseUrls(ClusterState.buildAgentsString());
      getSender().tell(message, getSelf());
    } else {
      System.out.println("fff");
      unhandled(message);
    }

  }
}
