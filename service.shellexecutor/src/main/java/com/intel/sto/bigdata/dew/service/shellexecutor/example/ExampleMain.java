package com.intel.sto.bigdata.dew.service.shellexecutor.example;

import com.intel.sto.bigdata.dew.app.AgentProxy;
import com.intel.sto.bigdata.dew.app.AppDes;
import com.intel.sto.bigdata.dew.app.DoNothingAppProcessor;
import com.intel.sto.bigdata.dew.service.shellexecutor.message.ExecuteRequest;

public class ExampleMain {

  public static void main(String[] args) throws Exception {
    if (args.length < 1) {
      System.err.println("Please input dew master url.");
      System.exit(1);
    }
    ExecuteRequest request = new ExecuteRequest();
    request.setDirectory("/tmp");
    request.setCommand("ls");
    request.setId("aaaaaaaaaa");
    new AgentProxy(args[0], new DoNothingAppProcessor(), new AppDes(null, "shell"))
        .requestService(request);
  }

}
