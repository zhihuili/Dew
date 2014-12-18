package com.intel.sto.bigdata.app.asp;

import java.util.Map;

import com.intel.sto.bigdata.app.asp.util.Util;

public class Update {

  public static void update(Map<String, String> conf) throws Exception {
    String path = conf.get("branch");
    String command = conf.get("update");
    Util.execute(command, null, path);
  }
}
