package com.intel.sto.bigdata.dew.service.sysmetrics.cli;

import com.intel.sto.bigdata.dew.app.AppListener;
import com.intel.sto.bigdata.dew.message.ServiceResponse;

public class PrintDstatListener extends AppListener {

  @Override
  public void process() {
    System.out.println("===============================================================");
    for (ServiceResponse response : responseList) {
      System.out.println(response.getNodeName() + "----" + response.getContent());
      System.out.println("===============================================================");
    }

  }

}
