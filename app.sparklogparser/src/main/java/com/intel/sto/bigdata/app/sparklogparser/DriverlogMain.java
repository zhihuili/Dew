package com.intel.sto.bigdata.app.sparklogparser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

  public static App parseLogFile(String fileName) {
    App app = new App();
    TimeAdjuster timer = new TimeAdjuster();
    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader(fileName));
      String line;
      while ((line = br.readLine()) != null) {
        timer.recordTime(line);
        Processor processor = Matcher.build(line);
        if (processor == null) {
          continue;
        }
        processor.apply(line).process(app);
      }
      timer.adjustTime(app);
    } catch (Exception e) {
      System.out.println("WARN: " + e.getMessage());
      e.printStackTrace();
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return app;
  }

  public static void printApp(App app, String path) {
    try {
      ExecutorsPrinter.print(app, path);
      NodePrinter.print(app, path);
      MemPrinter.print(app, path);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }

  }

  public static void processFile(String fileStr) throws Exception {
    App app = parseLogFile(fileStr);
    printApp(app, "/tmp/");
  }

}
