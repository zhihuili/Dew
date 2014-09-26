package com.intel.sto.bigdata.dew.service.sysmetrics;

import com.intel.sto.bigdata.dew.app.AgentProxy;

public class Main {

  /**
   * @param args
   */
  public static void main(String[] args) {
    new AgentProxy("akka.tcp://Master@127.0.0.1:2052/user/master", DstatListener.class)
        .requestService(new DstatServiceRequest(System.currentTimeMillis() - 3000, System
            .currentTimeMillis() - 1000));

  }

}
