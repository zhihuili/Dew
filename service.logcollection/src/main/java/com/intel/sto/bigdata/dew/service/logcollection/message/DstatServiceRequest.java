package com.intel.sto.bigdata.dew.service.logcollection.message;

import com.intel.sto.bigdata.dew.message.ServiceRequest;

public class DstatServiceRequest extends ServiceRequest {

  private static final long serialVersionUID = 9096938246864889217L;
  private long startTime;
  private long endTime;

  public DstatServiceRequest(long startTime, long endTime) {
    super("logCollection", "get");
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
