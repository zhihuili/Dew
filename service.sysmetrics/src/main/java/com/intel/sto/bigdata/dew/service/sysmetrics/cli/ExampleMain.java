package com.intel.sto.bigdata.dew.service.sysmetrics.cli;

import com.intel.sto.bigdata.dew.app.AgentProxy;
import com.intel.sto.bigdata.dew.service.sysmetrics.message.DstatServiceRequest;

public class ExampleMain {

  /**
   * @param args
   */
  public static void main(String[] args) {
    if (args.length < 1) {
      System.err.println("Please input master url.");
      System.exit(1);
    }
    new AgentProxy("akka.tcp://Master@" + args[0] + "/user/master", PrintDstatListener.class)
        .requestService(new DstatServiceRequest(System.currentTimeMillis() - 3000, System
            .currentTimeMillis() - 1000));
  }
}
