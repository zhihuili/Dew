package com.intel.sto.bigdata.dew.master;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.intel.sto.bigdata.dew.message.AgentInfo;

public class ClusterState {

  private static Map<String, AgentInfo> agents = new HashMap<String, AgentInfo>();

  public static void addAgent(String id, AgentInfo agent) {
    agents.put(id, agent);
  }

  public static String buildAgentsString() {
    if (agents.size() <= 0) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    for (Entry<String, AgentInfo> entry : agents.entrySet()) {
      sb.append(entry.getValue().getUrl());
      sb.append(";");
    }
    return sb.toString().substring(0, sb.length() - 1);
  }

}
