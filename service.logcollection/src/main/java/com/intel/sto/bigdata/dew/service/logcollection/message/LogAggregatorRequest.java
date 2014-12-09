package com.intel.sto.bigdata.dew.service.logcollection.message;

import com.intel.sto.bigdata.dew.message.ServiceRequest;

public class LogAggregatorRequest extends ServiceRequest {

  private static final long serialVersionUID = -2883175363597388580L;

  public LogAggregatorRequest() {
    super("logaggregation", "get");
  }
}
