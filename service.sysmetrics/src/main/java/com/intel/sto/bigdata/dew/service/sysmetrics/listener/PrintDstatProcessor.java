package com.intel.sto.bigdata.dew.service.sysmetrics.listener;

import java.util.List;

import com.intel.sto.bigdata.dew.app.AppProcessor;
import com.intel.sto.bigdata.dew.message.ServiceResponse;

public class PrintDstatProcessor implements AppProcessor {

  @Override
  public void process(List<ServiceResponse> responseList) {
    System.out.println("===============================================================");
    for (ServiceResponse response : responseList) {
      System.out.println(response.getNodeName() + "----" + response.getContent());
      System.out.println("===============================================================");
    }

  }
}
