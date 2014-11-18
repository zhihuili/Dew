package com.intel.sto.bigdata.app.appdiagnosis.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DstatUtil {

  public static String metricsHead =
      "used,buffer,cached,free,ioread,iowrit,usr,sys,idl,wai,hiq,siq,eth0recv,eth0send,eth1recv,eth1send,totalrecv,totalsend,diskread,diskwrit";

  private DstatUtil() {
  }

  /**
   * 
   * @param line dstat --mem --io --cpu --net -N eth0,eth1,total --disk
   * @return
   */
  public static Map<String, Double> parseDstat(List<String> line) {
    Map<String, Double> parseResult = new HashMap<String, Double>();

    String head[] = metricsHead.split(",");

    for (int i = 0; i < line.size(); i++) {
      parseResult.put(head[i], Double.valueOf(line.get(i)));
    }

    return parseResult;
  }
}
