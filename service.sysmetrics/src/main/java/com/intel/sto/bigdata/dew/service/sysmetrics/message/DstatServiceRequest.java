package com.intel.sto.bigdata.dew.service.sysmetrics.message;

import com.intel.sto.bigdata.dew.message.ServiceRequest;

public class DstatServiceRequest extends ServiceRequest {

  private static final long serialVersionUID = -135746986457132032L;
  long startTime;
  long endTime;

  public DstatServiceRequest(long startTime, long endTime) {
    super("dstat", "get");
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public long getStartTime() {
    return startTime;
  }

  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }

  public long getEndTime() {
    return endTime;
  }

  public void setEndTime(long endTime) {
    this.endTime = endTime;
  }

}
