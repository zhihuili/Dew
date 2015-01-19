package com.intel.sto.bigdata.dew.conf;

import java.io.IOException;
import java.util.Properties;

import com.intel.sto.bigdata.dew.utils.Util;

public class DewConf {
  Properties p;
  private static DewConf instance = new DewConf();

  private DewConf() {
    String dewHome = System.getenv("DEW_HOME");
    String confFile = dewHome + "/conf/dew.conf";
    try {
      p = Util.buildProperties(confFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static DewConf getDewConf() {
    return instance;
  }

  public String get(String key) {
    if (p != null) {
      return p.getProperty(key);
    }
    return null;
  }

  public Properties getP() {
    return p;
  }

  public void setP(Properties p) {
    this.p = p;
  }

}
