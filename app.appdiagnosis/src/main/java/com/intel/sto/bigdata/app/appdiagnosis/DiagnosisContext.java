package com.intel.sto.bigdata.app.appdiagnosis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiagnosisContext {

  private Map<Object, Object> map = new HashMap<Object, Object>();

  public void put(Object key, Object value) {
    map.put(key, value);
  }

  public Object get(Object key) {
    return map.get(key);
  }

  /**
   * 
   * @return [hostName,[[groupName,[[data]]]]]
   */
  public Map<String, List<Map<String, List<List<String>>>>> getPerformanceData() {
    return (Map<String, List<Map<String, List<List<String>>>>>) map.get("performanceData");
  }

  public void putPerformanceData(Map<String, List<Map<String, List<List<String>>>>> performanceData) {
    map.put("performanceData", performanceData);
  }
}
