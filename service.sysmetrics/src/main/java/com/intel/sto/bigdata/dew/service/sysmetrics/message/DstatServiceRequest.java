package com.intel.sto.bigdata.dew.service.sysmetrics.message;

import com.intel.sto.bigdata.dew.message.ServiceRequest;

public class DstatServiceRequest extends ServiceRequest {

  long startTime;
  long endTime;

  public DstatServiceRequest(long startTime, long endTime) {
    super("DstatService", "get");
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
