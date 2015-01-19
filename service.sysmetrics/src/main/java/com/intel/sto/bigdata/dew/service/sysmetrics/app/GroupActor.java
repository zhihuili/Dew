package com.intel.sto.bigdata.dew.service.sysmetrics.app;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

import com.intel.sto.bigdata.dew.message.AgentRegister;
import com.intel.sto.bigdata.dew.message.ServiceResponse;
import com.intel.sto.bigdata.dew.service.sysmetrics.message.CurrentDstatServiceRequest;

public class GroupActor extends UntypedActor {
  private CircleLink cl;
  private Map<AgentRegister, ActorRef> agentActors;
  private Logger log = Logger.getLogger(GroupActor.class);

  public GroupActor(CircleLink cl, Map<AgentRegister, ActorRef> agentActors) {
    this.cl = cl;
    this.agentActors = agentActors;
  }

  @Override
  public void onReceive(Object message) throws Exception {
    if (message instanceof CurrentDstatServiceRequest) {
      CircleLinkNode node =
          new CircleLinkNode(new HashMap<String, String>(), System.currentTimeMillis());
      cl.addItem(node);
      for (Entry<AgentRegister, ActorRef> e : agentActors.entrySet()) {
        e.getValue().tell(message, getSelf());
      }
    }
    if (message instanceof ServiceResponse) {
      ServiceResponse response = (ServiceResponse) message;
      if (response.getEm() != null) {
        log.error(response.getNodeName() + ":" + response.getEm());
        return;
      }
      CircleLinkNode node = cl.getTail();
      node.getElement().put(response.getNodeName(), response.getContent());
    }
  }

}
