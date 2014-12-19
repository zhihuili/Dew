package com.intel.sto.bigdata.app.asp.ui;

import com.intel.sto.bigdata.app.asp.Executor;
import com.intel.sto.bigdata.app.asp.timer.Timer;
import com.intel.sto.bigdata.app.asp.util.Util;

public class Asp {

  public static void main(String[] args) throws Exception {
    // Do it now and only once.
    if (args.length > 0) {
      Executor.execute();
      return;
    }
    Timer timer = new Timer();
    timer.schedule(Util.loadConf().get("time"));
  }

}
