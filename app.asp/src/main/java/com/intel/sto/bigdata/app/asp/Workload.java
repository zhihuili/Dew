package com.intel.sto.bigdata.app.asp;

import com.intel.sto.bigdata.app.asp.util.Util;

public class Workload {

  public static long run(String workloadName, String command) throws Exception {
    String[] s = command.split(";");
    long start = System.currentTimeMillis();
    String logFile = Util.buildLogFilePath(workloadName);
    Util.execute(s[1], null, s[0], logFile, 1800L);
    long end = System.currentTimeMillis();
    return end - start;
  }

}
