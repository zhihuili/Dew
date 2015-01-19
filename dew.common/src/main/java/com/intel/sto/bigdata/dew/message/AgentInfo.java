package com.intel.sto.bigdata.dew.message;

import java.io.Serializable;
import java.util.Map;

import akka.actor.ActorRef;

public class AgentInfo implements Serializable {

  private static final long serialVersionUID = 3685394835668340638L;

  private Map<AgentRegister, ActorRef> agentActors;

  public Map<AgentRegister, ActorRef> getAgentActors() {
    return agentActors;
  }

  public void setAgentActors(Map<AgentRegister, ActorRef> agentActors) {
    this.agentActors = agentActors;
  }

}
