package com.intel.sto.bigdata.app.sparkpowermeter.ui;

import com.intel.sto.bigdata.app.sparklogparser.util.Util;
import com.intel.sto.bigdata.app.sparkpowermeter.OfflineExecutor;

public class OfflineAnalysis {

  public static void main(String[] args) throws Exception {

    // analyze spark log file.
    if (args.length == 3) {
      OfflineExecutor.execute(args[0], args[1], args[2]);
    }

    // analyze duration.
    else if (args.length == 6) {
      // TODO refactor all these Util.
      long startTime = Util.transformTime(args[2] + " " + args[3]);
      long endTime = Util.transformTime(args[4] + " " + args[5]);
      OfflineExecutor.execute(args[0], args[1], startTime, endTime);
    }

    else {
      System.err.println("Usage:");
      System.err.println("Analyze spark log file: analyze.sh log_file_path");
      System.err
          .println("Analyze duration: analyze.sh startTime(yy/MM/dd HH:mm:ss) endTime(yy/MM/dd HH:mm:ss)");
      System.exit(1);
    }
  }
}
