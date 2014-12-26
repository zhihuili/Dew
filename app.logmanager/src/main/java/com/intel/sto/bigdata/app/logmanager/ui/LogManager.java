package com.intel.sto.bigdata.app.logmanager.ui;

import com.intel.sto.bigdata.app.logmanager.LogCollection;
import com.intel.sto.bigdata.app.logmanager.LogQuery;

public class LogManager {

  public static void main(String[] args) throws Exception {

    // collect spark executor log file.
    if (args.length > 1) {
      if (args[0].trim().equals("-collect")) {
        LogCollection.collect(args[1]);
        return;
      }
      if (args[0].trim().equals("-query") && args.length > 2) {
        String words = null;
        for (int i = 2; i < args.length; i++) {
          if (words == null) {
            words = args[i];
          } else {
            words = words + " " + args[i];
          }
        }
        LogQuery.query(args[1], words);
      }
    }

    System.err.println("Usage:");
    System.err.println("Collect spark executor log file: logmanager.sh -collect appId");
    System.err.println("Query spark executor log file: logmanager.sh -query appId queryWords");
    System.exit(1);
  }
}
