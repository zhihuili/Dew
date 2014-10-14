package com.intel.sto.bigdata.dew.master;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.intel.sto.bigdata.dew.master.servicemanagement.DefaultServiceManager;
import com.intel.sto.bigdata.dew.message.AgentRegister;

public class ClusterState {

  private static Set<AgentRegister> agents = new HashSet<AgentRegister>();
  private static Map<String, String> serviceMap = new HashMap<String, String>();

  public static void initService() {
    serviceMap.putAll(DefaultServiceManager.getServiceMap());
  }

  public static void addAgent(AgentRegister agent) {
    agents.add(agent);
  }

  public static Set<AgentRegister> buildAgentString(Set<String> hosts) {
    if (hosts == null || hosts.size() == 0) {
      return agents;
    }
    Set<AgentRegister> urls = new HashSet<AgentRegister>();
    for (AgentRegister ar : agents) {
      if (hosts.contains(ar.getHostName()) || hosts.contains(ar.getIp())) {
        urls.add(ar);
      }
    }
    return urls;
  }

  public static Map<String, String> getServiceMap() {
    return serviceMap;
  }

}
