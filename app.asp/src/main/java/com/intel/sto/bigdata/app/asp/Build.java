package com.intel.sto.bigdata.app.asp;

import java.util.Properties;

import com.intel.sto.bigdata.app.asp.util.Util;

public class Build {
  public static void build(Properties conf) throws Exception {
    String path = conf.getProperty("branch");
    String command = conf.getProperty("build");
    if (command != null && !command.trim().equals("")) {
      Util.execute(command, null, path);
    }
  }
}
