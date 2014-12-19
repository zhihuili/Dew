package com.intel.sto.bigdata.app.asp;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.intel.sto.bigdata.app.asp.util.Util;

public class Executor {
  public static void execute() throws Exception {
    Map<String, String> conf = Util.loadConf();
    Update.update(conf);
    Build.build(conf);
    Map<String, String> workload = Util.loadWorkload();
    Map<String, Long> workresult = new HashMap<String, Long>();
    for (Entry<String, String> entry : workload.entrySet()) {
      String workloadName = entry.getKey();
      String workloadCommand = entry.getValue();
      long duration = Workload.run(workloadCommand);
      workresult.put(workloadName, duration);
    }
    DataPrinter.print(conf, workresult);
  }

}
