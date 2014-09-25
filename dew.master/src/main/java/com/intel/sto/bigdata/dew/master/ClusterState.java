package com.intel.sto.bigdata.dew.master;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.intel.sto.bigdata.dew.message.AgentRegister;

public class ClusterState {

  private static Map<String, AgentRegister> agents = new HashMap<String, AgentRegister>();

  public static void addAgent(String id, AgentRegister agent) {
    agents.put(id, agent);
  }

  public static String buildAgentsString() {
    if (agents.size() <= 0) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    for (Entry<String, AgentRegister> entry : agents.entrySet()) {
      sb.append(entry.getValue().getUrl());
      sb.append(";");
    }
    return sb.toString().substring(0, sb.length() - 1);
  }

}
