package com.intel.sto.bigdata.app.webcenter.logic.action.bean;

public class AppBean {
  private int appId;
  private String name;
  private String path;
  private String executable;
  private String strategy;
  private String type;
  private String host;

  public int getAppId() {
    return appId;
  }

  public void setAppId(int appId) {
    this.appId = appId;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getExecutable() {
    return executable;
  }

  public void setExecutable(String executable) {
    this.executable = executable;
  }

  public String getStrategy() {
    return strategy;
  }

  public void setStrategy(String strategy) {
    this.strategy = strategy;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}