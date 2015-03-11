package com.intel.sto.bigdata.dew.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.intel.sto.bigdata.dew.service.ServiceDes;

public class Files {

  private Files() {
  }

  public static File getDstatDataPath() {
    File dir = new File(".");
    try {
      File result = new File(dir.getCanonicalFile(), "dew-collected-data/dstat");
      if (!result.exists()) {
        result.mkdirs();
      }
      return result;
    } catch (Exception e) {
      return null;
    }
  }

  public static Set<String> loadResourceFile(InputStream is) throws Exception {
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    Set<String> lines = new HashSet<String>();
    String line;
    while ((line = br.readLine()) != null) {
      if (!line.startsWith("#")) {
        lines.add(line);
      }
    }
    br.close();
    return lines;
  }

  public static Map<String, String> loadPropertiesFile(InputStream is) throws Exception {
    Set<String> set = loadResourceFile(is);
    Map<String, String> map = new HashMap<String, String>();
    for (String line : set) {
      if (line == null || line.trim().equals("")) {
        continue;
      }
      String[] kv = line.split("=");
      if (kv == null || kv.length != 2) {
        throw new Exception("Failed load properties file because of " + line);
      }
      map.put(kv[0].trim(), kv[1].trim());
    }
    return map;
  }

  public static Map<String, String> loadPropertiesFile(String fileName) throws Exception {
    return loadPropertiesFile(Files.class.getResourceAsStream(fileName));
  }

  public static Set<String> loadResourceFile(String fileName) throws Exception {
    return loadResourceFile(Files.class.getResourceAsStream(fileName));
  }

  public static List<ServiceDes> loadService(String fileName) throws Exception {
    Map<String, String> map = loadPropertiesFile(fileName);
    List<ServiceDes> list = new ArrayList<ServiceDes>();
    for (Entry<String, String> entry : map.entrySet()) {
      ServiceDes des = new ServiceDes();
      des.setServiceName(entry.getKey());
      String[] serviceDes = entry.getValue().split(";");
      if (serviceDes.length != 2) {
        throw new Exception("illegal service configuration");
      }
      String serviceType = serviceDes[1];
      String serviceClass = serviceDes[0];
      des.setServiceClass(serviceClass);
      des.setServiceType(serviceType);
      des.getContext().put(Constants.MASTER_CURRENT_TIME, String.valueOf(System.currentTimeMillis()));
      list.add(des);
    }
    return list;
  }

  public static void main(String[] args) {
    System.out.println(getDstatDataPath());
  }
}
