package com.intel.sto.bigdata.app.asp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.intel.sto.bigdata.app.asp.chart.ChartProcessor;
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

  public static void execute() {
    executeWorkload();
    draw();
    report();
  }

  public static void executeWorkload() {
    try {
      Update.update(conf);
      Build.build(conf);
      Map<String, String> workload = Util.loadWorkload();
      Map<String, Long> workresult = new HashMap<String, Long>();
      for (Entry<String, String> entry : workload.entrySet()) {
        String workloadName = entry.getKey();
        String workloadCommand = entry.getValue();
        try {
          long duration = Workload.run(workloadName, workloadCommand);
          workresult.put(workloadName, duration);
        } catch (Exception e) {
          System.out.println("==========error in " + workloadName + "==========");
          System.out.println(e.getMessage());
          e.printStackTrace();
        } finally { // kill spark process
          Runtime runtime = Runtime.getRuntime();
          Process process = runtime.exec("jps");
          java.io.InputStream is = process.getInputStream();
          BufferedReader br = new BufferedReader(new InputStreamReader(is));
          String line;
          try {
            while ((line = br.readLine()) != null) {
              if(line.contains("SparkSubmit")) {              
                String killCmd = "kill -9 "+line.split(" ")[0];
                runtime.exec(killCmd);
              }
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
      String time = DataPrinter.print(conf, workresult);
      ListBuilder.buildList(conf, MAX_X_SIZE, time);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }

  public static void executeNow() {
    execute();
  }

  public static void draw() {
    try {
      new ChartProcessor().process(conf);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }

  public static void report() {
    String path = com.intel.sto.bigdata.dew.utils.Util.getDewHome() + "/app.sparkreport";
    String command = "./report.sh " + Util.buildLogPath();
    try {
      Util.execute(command, null, path);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }
}
