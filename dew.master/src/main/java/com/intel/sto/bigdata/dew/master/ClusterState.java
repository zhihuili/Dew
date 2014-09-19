package com.intel.sto.bigdata.dew.master;

import java.util.HashMap;
import java.util.Map;

import com.intel.sto.bigdata.dew.message.AgentInfo;

public class ClusterState {

  private static Map<String, AgentInfo> agents = new HashMap<String, AgentInfo>();

  public static void addAgent(String id, AgentInfo agent) {
    agents.put(id, agent);
  }

}
