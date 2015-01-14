package com.intel.sto.bigdata.app.sparklogparser.util;

import java.io.File;
import java.io.FileOutputStream;

import com.intel.sto.bigdata.app.sparklogparser.model.App;

public class ExecutorsPrinter {
  public static void print(App app, String path) throws Exception {

    FileOutputStream executorsFs = new FileOutputStream(new File(path , "executors.csv"));
    for (String executor : app.getExecutors()) {
      executorsFs.write((executor + "\r\n").getBytes());
    }
    executorsFs.close();
  }

}
