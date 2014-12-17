package com.intel.sto.bigdata.app.webcenter.logic.action.bean;

public class appBean {
  public int appId;
  public String name;
  public String path;
  public String executable;
  public String strategy;
  public String type;

  public int getApp_id() {
    return appId;
  }

  public void setApp_id(int app_id) {
    this.appId = app_id;
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
