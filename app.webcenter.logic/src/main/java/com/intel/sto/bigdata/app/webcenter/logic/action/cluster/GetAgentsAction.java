package com.intel.sto.bigdata.app.webcenter.logic.action.cluster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import akka.actor.ActorRef;

import com.intel.sto.bigdata.dew.app.AppDes;
import com.intel.sto.bigdata.dew.app.driver.MyAppDriver;
import com.intel.sto.bigdata.dew.message.AgentRegister;
import com.opensymphony.xwork2.ActionSupport;

public class GetAgentsAction extends ActionSupport {
  private static final long serialVersionUID = -6671512345051496331L;

  private MyAppDriver driver;
  private Map<AgentRegister, ActorRef> agentActors;
  private List<AgentRegister> agents;

  public List<AgentRegister> getAgents() {
    return agents;
  }

  public void setAgents(List<AgentRegister> agents) {
    this.agents = agents;
  }

  public String execute() throws Exception {
    driver = MyAppDriver.getMyAppDriver();
    agentActors = driver.getAgents(new AppDes(null, null));

    agents = new ArrayList<AgentRegister>();
    for (Map.Entry<AgentRegister, ActorRef> entry : agentActors.entrySet()) {
      agents.add(entry.getKey());
    }
    
    agentSort(agents);

    return SUCCESS;
  }

  private void agentSort(List<AgentRegister> agents) {
    Collections.sort(agents, new Comparator<AgentRegister>() {
      public int compare(AgentRegister arg0, AgentRegister arg1) {
        if (!arg0.getHostName().equals(arg1.getHostName())) {
          return arg0.getHostName().compareTo(arg1.getHostName());
        } else if (!arg0.getType().equals(arg1.getType())) {
          return arg0.getType().compareTo(arg1.getType());
        } else {
          return arg0.getServices().size() - arg1.getServices().size();
        }
      }
    });
  }
}
