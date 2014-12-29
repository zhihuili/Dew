package com.intel.sto.bigdata.app.webcenter.logic.action.bean;

public class JobBean {
  public int jobId;
  public String name;
  public String defination;
  public String cycle;
  public int userId;

  public int getJobId() {
    return jobId;
  }

  public void setJobId(int jobId) {
    this.jobId = jobId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDefination() {
    return defination;
  }

  public void setDefination(String defination) {
    this.defination = defination;
  }

  public String getCycle() {
    return cycle;
  }

  public void setCycle(String cycle) {
    this.cycle = cycle;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }
}