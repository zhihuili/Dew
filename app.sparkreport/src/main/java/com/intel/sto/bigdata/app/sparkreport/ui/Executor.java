package com.intel.sto.bigdata.app.sparkreport.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.intel.sto.bigdata.app.sparkreport.process.SparkLogProcessor;
import com.intel.sto.bigdata.app.sparkreport.report.OutputBuilder;
import com.intel.sto.bigdata.app.sparkreport.report.Result;
import com.intel.sto.bigdata.app.sparkreport.report.SparkResultBuilder;

public class Executor {

  public void execute(String path) {
    File dir = new File(path);
    if (!dir.exists() || !dir.isDirectory()) {
      System.out.println("Target directory is not exists");
      return;
    }
    File[] files = dir.listFiles();
    if (files == null || files.length == 0) {
      System.out.println("Target directory is not include any files.");
      return;
    }

    // process
    for (File file : files) {
      try {
        SparkLogProcessor.getInstance().process(file);
      } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
      }
    }

    // build
    List<Result> resultList = new ArrayList<Result>();
    for (File file : files) {
      try {
        resultList.add(SparkResultBuilder.getInstance().build(file));
      } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
      }
    }

    // output
    StringBuilder out = OutputBuilder.getInstance().build(resultList);
    OutputBuilder.getInstance().printSystem(out);

  }

}
