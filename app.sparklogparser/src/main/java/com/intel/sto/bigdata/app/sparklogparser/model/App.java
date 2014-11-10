package com.intel.sto.bigdata.app.sparklogparser.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.intel.sto.bigdata.app.sparklogparser.common.MemInfo;

public class App extends Node<Job, Object> {
  public String CURRENT_TASK_SET = "currentTaskSet";
  // FIXME assume job is NOT concurrent.
  public String CURRENT_JOB = "currentJob";
  private Map<String, MemInfo> memMap = new HashMap<String, MemInfo>();
  private Set<String> executors = new HashSet<String>();

  private String userName;
  private String appId;

  public Map<String, MemInfo> getMemMap() {
    return memMap;
  }

  public Set<String> getExecutors() {
    return executors;
  }

  private Map<String, Node> context = new HashMap<String, Node>();

  public Map<String, Node> getContext() {
    return context;
  }

  public void setContext(Map<String, Node> context) {
    this.context = context;
  }

  public void addRddMem(String nodeId, String rddId, float memSize, long time) {
    if (memMap.containsKey(nodeId)) {
      memMap.get(nodeId).addRddMemInfo(rddId, time, memSize);
    } else {
      memMap.put(nodeId, new MemInfo(rddId, time, memSize));
    }
  }

  public void addNode(String executor) {
    executors.add(executor);
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getAppId() {
    return appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

}
