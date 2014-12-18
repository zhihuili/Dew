package com.intel.sto.bigdata.app.asp;

import java.util.Map;

import com.intel.sto.bigdata.app.asp.util.Util;

public class Build {
  public static void build(Map<String, String> conf) throws Exception {
    String path = conf.get("branch");
    String command = conf.get("build");
    Util.execute(command, null, path);
  }
}
