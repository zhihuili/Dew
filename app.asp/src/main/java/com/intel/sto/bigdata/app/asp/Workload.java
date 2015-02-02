package com.intel.sto.bigdata.app.asp;

import java.io.File;

import com.intel.sto.bigdata.app.asp.util.Util;

public class Workload {

  public static long run(String workloadName, String command) throws Exception {
    String[] s = command.split(";");
    long start = System.currentTimeMillis();
    String logFile = buildLogFilePath(workloadName);
    Util.execute(s[1], null, s[0], logFile);
    long end = System.currentTimeMillis();
    return end - start;
  }

  private static String buildLogFilePath(String name) {
    String time = com.intel.sto.bigdata.dew.utils.Util.getCurrentYYYYMMDD();
    String dewHome = com.intel.sto.bigdata.dew.utils.Util.getDewHome();
    String logPath = dewHome + "/app.asp/logs/" + time;
    File logPathFile = new File(logPath);
    if (logPathFile.exists()) {
      logPathFile.mkdirs();
    }
    return logPath + "/" + name;
  }

}
