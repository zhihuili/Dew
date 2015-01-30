package com.intel.sto.bigdata.app.asp;

import java.util.Properties;

import com.intel.sto.bigdata.app.asp.util.Util;

public class Update {

  public static void update(Properties conf) throws Exception {
    String path = conf.getProperty("branch");
    String command = conf.getProperty("update");
    if (command != null && !command.trim().equals("")) {
      Util.execute(command, null, path);
    }
  }
}
