package com.intel.sto.bigdata.app.asp;

import com.intel.sto.bigdata.app.asp.util.Util;

public class Workload {

  public static long run(String command) throws Exception {
    long start = System.currentTimeMillis();
    Util.execute(command, null, null);
    long end = System.currentTimeMillis();
    return end - start;
  }

}
