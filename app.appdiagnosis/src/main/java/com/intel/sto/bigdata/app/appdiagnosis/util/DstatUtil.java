package com.intel.sto.bigdata.app.appdiagnosis.util;

import java.util.List;
import java.util.Map;

public class DstatUtil {

  private DstatUtil() {
  }

  /**
   * 
   * @param line dstat --mem --io --cpu --net -N eth0,eth1,total --disk
   * @return
   */
  public static Map<String, Double> parseDstat(List<String> line) {
    // TODO
    return null;
  }
}
