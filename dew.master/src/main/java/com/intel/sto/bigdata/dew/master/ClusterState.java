package com.intel.sto.bigdata.dew.master;

import java.util.HashSet;
import java.util.Set;

import com.intel.sto.bigdata.dew.message.AgentRegister;

public class ClusterState {

  private static Set<AgentRegister> agents = new HashSet<AgentRegister>();

  public static void addAgent(AgentRegister agent) {
    agents.add(agent);
  }

  public static void addService(String agentUrl, String serviceName) {
    //TODO use map replace set
    for (AgentRegister ar : agents) {
      if (ar.getUrl().equals(agentUrl)) {
        ar.getServices().add(serviceName);
        return;
      }
    }
  }

  public static Set<AgentRegister> findAgent(Set<String> hosts, String service) {
    if (hosts == null && service == null) {
      return agents;
    }
    Set<AgentRegister> urls = new HashSet<AgentRegister>();
    for (AgentRegister ar : agents) {
      if (hosts == null || hosts.contains(ar.getHostName()) || hosts.contains(ar.getIp())) {
        if (service == null || ar.getServices().contains(service)) {
          urls.add(ar);
        }
      }
    }
    return urls;
  }

}
