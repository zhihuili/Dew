package com.intel.sto.bigdata.app.asp.ui;

import com.intel.sto.bigdata.app.asp.Executor;
import com.intel.sto.bigdata.app.asp.timer.Timer;
import com.intel.sto.bigdata.app.asp.util.Util;

public class Asp {

  public static void main(String[] args) throws Exception {
    // Do it now and only once.
    if (args.length > 0) {
      if (args[0].equals("now")) {
        Executor.executeNow();
      } else if (args[0].equals("workload")) {
        Executor.executeWorkload();
      } else if (args[0].equals("draw")) {
        Executor.draw();
      } else if (args[0].equals("report")) {
        Executor.report();
      } else {
        System.err.println("Useage:");
        System.err.println("now  -execute asp at once.");
        System.err.println("workload  -execute workload only at noce.");
        System.err.println("draw  -draw asp chart at once.");
      }
      return;
    }
    Timer timer = new Timer();
    timer.schedule(Util.loadConf().getProperty("time"));
  }

}
