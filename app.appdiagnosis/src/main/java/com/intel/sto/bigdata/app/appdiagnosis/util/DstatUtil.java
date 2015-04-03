package com.intel.sto.bigdata.app.appdiagnosis.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DstatUtil {

  public static String[] metricsHead = { "used", "buffer", "cached", "free", "ioread", "iowrit",
      "usr", "sys", "idl", "wai", "hiq", "siq", "totalrecv", "totalsend", "diskread", "diskwrit" };

  private DstatUtil() {
  }

  /**
   * 
   * @param line dstat --mem --io --cpu --net -N eth0,eth1,total --disk
   * @return
   */
  public static Map<String, Double> parseDstat(List<String> line) {
    Map<String, Double> parseResult = new HashMap<String, Double>();

    for (int i = 0; i < line.size(); i++) {
      parseResult.put(metricsHead[i], Double.valueOf(line.get(i)));
    }

    return parseResult;
  }

  public static Map<String, Double> parseDstat(String[] line) {
    Map<String, Double> parseResult = new HashMap<String, Double>();

    for (int i = 0; i < line.length; i++) {
      parseResult.put(metricsHead[i], Double.valueOf(line[i]));
    }

    return parseResult;
  }
}
