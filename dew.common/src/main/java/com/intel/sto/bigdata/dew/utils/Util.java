package com.intel.sto.bigdata.dew.utils;

import java.net.URL;

public class Util {
  // Because posting http stream use header to transfer parameter, add a
  // prefix to differ from http protocol.
  private static String PREFIX = "dew-";

  public static String addPrefix(String s) {
    return PREFIX + s;
  }

  public static String removePrefix(String s) {
    if (s.startsWith(PREFIX)) {
      return s.substring(4);
    }
    return null;
  }

  /**
   * Only used for master and agents, and suppose starting them by shell script.
   */
  public static String findDewClassPath() {
    String dewHome = System.getenv("DEW_HOME");
    if (dewHome != null) {
      return dewHome + "/dew.assembly/dew.jar";
    }
    URL url = Thread.currentThread().getContextClassLoader().getResource("");
    if (url != null) {
      return url.getPath();
    }
    return null;
  }
}
