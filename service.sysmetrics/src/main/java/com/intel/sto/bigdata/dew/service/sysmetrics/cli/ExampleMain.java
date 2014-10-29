package com.intel.sto.bigdata.dew.service.sysmetrics.cli;

import com.intel.sto.bigdata.dew.app.AgentProxy;
import com.intel.sto.bigdata.dew.app.AppDes;
import com.intel.sto.bigdata.dew.service.sysmetrics.listener.PrintDstatProcessor;
import com.intel.sto.bigdata.dew.service.sysmetrics.message.DstatServiceRequest;

public class ExampleMain {

  /**
   * @param args
   * @throws Exception 
   */
  public static void main(String[] args) throws Exception {
    if (args.length < 1) {
      System.err.println("Please input master url.");
      System.exit(1);
    }
    new AgentProxy(args[0], new PrintDstatProcessor(), new AppDes())
        .requestService(new DstatServiceRequest(System.currentTimeMillis() - 3000, System
            .currentTimeMillis() - 1000));
  }
}
