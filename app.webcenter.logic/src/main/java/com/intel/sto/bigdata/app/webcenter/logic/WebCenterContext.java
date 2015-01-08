package com.intel.sto.bigdata.app.webcenter.logic;

import java.util.HashMap;
import java.util.Map;

public class WebCenterContext {
  private static Map<Object, Object> context = new HashMap<Object, Object>();
  private static Map<String, String> conf;

  public static void put(Object key, Object value) {
    context.put(key, value);
  }

  public static Object get(Object key) {
    return context.get(key);
  }

  public static void putConf(Map<String, String> c) {
    conf = c;
  }

  public static Map<String, String> getConf() {
    return conf;
  }
}
