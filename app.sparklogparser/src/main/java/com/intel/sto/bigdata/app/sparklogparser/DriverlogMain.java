package com.intel.sto.bigdata.app.sparklogparser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Executors;

import com.intel.sto.bigdata.app.sparklogparser.model.App;
import com.intel.sto.bigdata.app.sparklogparser.processor.Processor;
import com.intel.sto.bigdata.app.sparklogparser.util.ExecutorsPrinter;
import com.intel.sto.bigdata.app.sparklogparser.util.MemPrinter;
import com.intel.sto.bigdata.app.sparklogparser.util.NodePrinter;
import com.intel.sto.bigdata.app.sparklogparser.util.TimeAdjuster;

public class DriverlogMain {
  public static void main(String[] args) throws Exception {
    if (args.length < 1) {
      System.err.println("Please input Spark Driver's log file path.");
      System.exit(1);
    }
    String fileStr = args[0];
    long start = System.currentTimeMillis();
    processFile(fileStr);
    long end = System.currentTimeMillis();
    System.out.println("Parsed spark log, cost time(ms):" + (end - start));
    System.out
        .println("Complete analysis, please check output file(/tmp/job.csv, stage.csv, task.csv)");
  }

  public static App parseLogFile(String fileName){
    App app = new App();
    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader(fileName));
      String line;
      while ((line = br.readLine()) != null) {
        TimeAdjuster.recordTime(line);
        Processor processor = Matcher.build(line);
        if (processor == null) {
          continue;
        }
        processor.apply(line).process(app);
      }
      TimeAdjuster.adjustTime(app);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
    return app;
  }

  public static void printApp(App app, String path) throws Exception {
    NodePrinter.print(app, path);
    MemPrinter.print(app, path);
    ExecutorsPrinter.print(app, path);
  }

  public static void processFile(String fileStr) throws Exception {
    App app = parseLogFile(fileStr);
    printApp(app, "/tmp/");
  }

}
