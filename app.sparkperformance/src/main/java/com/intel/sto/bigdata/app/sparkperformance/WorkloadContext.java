package com.intel.sto.bigdata.app.sparkperformance;

import java.util.HashMap;
import java.util.Map;

/**
 * FIXME thread safe in web ui.
 *
 */
public class WorkloadContext {

  private static Map<Object, Object> map = new HashMap<Object, Object>();

  public static void put(Object key, Object value) {
    map.put(key, value);
  }

  public static Object get(Object key) {
    return map.get(key);
  }
}
