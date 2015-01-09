package com.intel.sto.bigdata.app.webcenter.logic.action.bean;

import java.sql.Timestamp;

public class AppRecordBean {
  private String recordID;
  private String appName;
  private String jobRecordID;
  private Timestamp startTime;
  private Timestamp endTime;
  private String result;

  public String getRecordID() {
    return recordID;
  }

  public void setRecordID(String recordID) {
    this.recordID = recordID;
  }

  public String getAppName() {
    return appName;
  }

  public void setAppName(String appName) {
    this.appName = appName;
  }

  public String getJobRecordID() {
    return jobRecordID;
  }

  public void setJobRecordID(String jobRecordID) {
    this.jobRecordID = jobRecordID;
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
