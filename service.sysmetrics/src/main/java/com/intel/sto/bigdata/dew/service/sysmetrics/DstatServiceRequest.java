package com.intel.sto.bigdata.dew.service.sysmetrics;

import com.intel.sto.bigdata.dew.message.ServiceRequest;

public class DstatServiceRequest extends ServiceRequest {

  public DstatServiceRequest(long startTime, long endTime) {
    super("dstatService", "get");
  }
}
