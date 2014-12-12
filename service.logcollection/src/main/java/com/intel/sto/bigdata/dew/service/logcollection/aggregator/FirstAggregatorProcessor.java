package com.intel.sto.bigdata.dew.service.logcollection.aggregator;

import java.util.List;

import com.intel.sto.bigdata.dew.app.AppProcessor;
import com.intel.sto.bigdata.dew.message.ServiceResponse;

public class FirstAggregatorProcessor implements AppProcessor {

  private String httpUrl;

  @Override
  public void process(List<ServiceResponse> responseList) {
    for (ServiceResponse sr : responseList) {
      if (sr.getEm() == null && sr.getContent() != null) {
        httpUrl = sr.getContent();
        break;
      }
    }

  }

  public String getHttpUrl() {
    return httpUrl;
  }
}
