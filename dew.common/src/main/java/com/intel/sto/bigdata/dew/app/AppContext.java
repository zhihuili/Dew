package com.intel.sto.bigdata.dew.app;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AppContext {
  private Set<String> hostSet;
  private Map<Object, Object> map = new HashMap<Object, Object>();

  public void set(Object key, Object value) {
    map.put(key, value);
  }

  public Object get(Object key) {
    return map.get(key);
  }

  public Set<String> getHostSet() {
    return hostSet;
  }

  public void setHostSet(Set<String> hostSet) {
    this.hostSet = hostSet;
  }

}
