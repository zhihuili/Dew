package com.intel.sto.bigdata.app.sparkreport.report;

public class Result {
  private String appName;
  private String appId;
  private String startTime;
  private String endTime;
  private String exceptionMessage;
  private String duration;

  public String getAppName() {
    return appName;
  }

  public void setAppName(String appName) {
    this.appName = appName;
  }

  public String getAppId() {
    return appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public String getExceptionMessage() {
    return exceptionMessage == null ? "N/A" : exceptionMessage;
  }

  public void setExceptionMessage(String exceptionMessage) {
    exceptionMessage = exceptionMessage;
  }

  public String getDuration() {
    return duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }

}
