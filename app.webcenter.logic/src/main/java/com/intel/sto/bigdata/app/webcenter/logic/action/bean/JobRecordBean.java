package com.intel.sto.bigdata.app.webcenter.logic.action.bean;

import java.sql.Timestamp;

public class JobRecordBean {
  private String recordID;
  private String jobName;
  private Timestamp startTime;
  private Timestamp endTime;
  private String result;

  public String getRecordID() {
    return recordID;
  }

  public void setRecordID(String recordID) {
    this.recordID = recordID;
  }

  public String getJobName() {
    return jobName;
  }

  public void setJobName(String jobName) {
    this.jobName = jobName;
  }

  public Timestamp getStartTime() {
    return startTime;
  }

  public void setStartTime(Timestamp startTime) {
    this.startTime = startTime;
  }

  public Timestamp getEndTime() {
    return endTime;
  }

  public void setEndTime(Timestamp endTime) {
    this.endTime = endTime;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }
}
