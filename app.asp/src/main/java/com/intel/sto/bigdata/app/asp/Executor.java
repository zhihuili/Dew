package com.intel.sto.bigdata.app.asp;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.intel.sto.bigdata.app.asp.util.Util;

public class Executor {

  private static Properties conf;
  private static final int MAX_X_SIZE = 9;
  static {
    try {
      conf = Util.loadConf();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void execute() throws Exception {
    executeWorkload();
    draw();
  }

  public static void executeWorkload() throws Exception {
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
    String time = DataPrinter.print(conf, workresult);
    ListBuilder.buildList(conf, MAX_X_SIZE, time);
  }

  public static void executeNow() throws Exception {
    execute();
  }

  public static void draw() throws Exception {
    new DrawChart().draw(conf);
  }
}
