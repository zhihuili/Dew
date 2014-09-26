package com.intel.sto.bigdata.dew.master.servicemanagement;

import java.util.HashMap;
import java.util.Map;

public class DefaultServiceManager {

  private static Map<String, String> serviceMap = new HashMap<String, String>();

  static {
    serviceMap.put("dstat", "com.intel.sto.bigdata.dew.service.sysmetrics.DstatService");
  }

  public static Map<String, String> getServiceMap() {
    return serviceMap;
  }
}
