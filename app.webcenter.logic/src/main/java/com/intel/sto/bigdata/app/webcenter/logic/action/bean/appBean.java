package com.intel.sto.bigdata.app.webcenter.logic.action.bean;

public class appBean {
  public int app_id;
  public String name;
  public String path;
  public String executable;
  public String strategy;
  public String type;

  public int getApp_id() {
    return app_id;
  }

  public void setApp_id(int app_id) {
    this.app_id = app_id;
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
