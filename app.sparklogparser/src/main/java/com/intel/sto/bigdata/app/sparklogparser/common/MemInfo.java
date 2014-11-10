package com.intel.sto.bigdata.app.sparklogparser.common;

import java.util.HashMap;
import java.util.Map;

public class MemInfo {
  private Map<String, rddMemInfo> rddMemInfoMap = new HashMap<String, rddMemInfo>();

  public Map<String, rddMemInfo> getRddMemInfoMap() {
    return rddMemInfoMap;
  }

  public MemInfo(String rddId, long inputTime, float inputMemSize) {
    rddMemInfoMap.put(rddId, new rddMemInfo(inputTime, inputMemSize));
  }

  public void addRddMemInfo(String rddId, long inputTime, float inputMemSize) {
    if (rddMemInfoMap.containsKey(rddId)) {
      rddMemInfoMap.get(rddId).addMemSize(inputMemSize);
      if (rddMemInfoMap.get(rddId).getTime() < inputTime) {
        rddMemInfoMap.get(rddId).setTime(inputTime);
      }
    } else {
      rddMemInfoMap.put(rddId, new rddMemInfo(inputTime, inputMemSize));
    }
  }

}
