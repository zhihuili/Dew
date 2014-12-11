package com.intel.sto.bigdata.app.logmanager.ui;

import com.intel.sto.bigdata.app.logmanager.LogCollection;

public class LogManager {

  public static void main(String[] args) throws Exception {

    // collect spark executor log file.
    if (args.length > 1) {
      LogCollection.collect(args[1]);
    }

    else {
      System.err.println("Usage:");
      System.err.println("Collect spark executor log file: logmanager.sh collect appId");
      System.exit(1);
    }
  }
}
