package com.intel.sto.bigdata.app.webcenter.logic;

import java.util.HashMap;
import java.util.Map;

public class WebCenterContext {
  private static Map<Object, Object> context = new HashMap<Object, Object>();

  public static void put(Object key, Object value) {
    context.put(key, value);
  }

  public static Object get(Object key) {
    return context.get(key);
  }
}
