package com.intel.sto.bigdata.dew.service.sysmetrics.message;

import com.intel.sto.bigdata.dew.message.ServiceRequest;

public class CurrentDstatServiceRequest extends ServiceRequest {
  private static final long serialVersionUID = -7897618112033583216L;

  public CurrentDstatServiceRequest(){
    super("dstat","get");
  }
}
