package com.intel.sto.bigdata.dew.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

public class Util {
  // Because posting http stream use header to transfer parameter, add a
  // prefix to differ from http protocol.
  private static String PREFIX = "dew-";
  private static Set<String> hosts;

  public static String addPrefix(String s) {
    return PREFIX + s;
  }

  public static String removePrefix(String s) {
    if (s.startsWith(PREFIX)) {
      return s.substring(4);
    }
    return null;
  }

  /**
   * Only used for master and agents, and suppose starting them by shell script.
   */
  public static String findDewClassPath() {
    String dewHome = System.getenv("DEW_HOME");
    if (dewHome != null) {
      return dewHome + "/dew.assembly/dew.jar";
    }
    URL url = Thread.currentThread().getContextClassLoader().getResource("");
    if (url != null) {
      return url.getPath();
    }
    return null;
  }

  public static String getDewHome() {
    return System.getenv("DEW_HOME");
  }

  public static Properties buildProperties(String conf) throws IOException {
    InputStream in = new BufferedInputStream(new FileInputStream(conf));
    Properties props = new Properties();
    props.load(in);
    in.close();
    return props;
  }

  public synchronized static Set<String> loadSlaves() throws Exception {
    if (hosts == null) {
      String dewHome = System.getenv("DEW_HOME");
      File slaveFile = new File(dewHome + "/conf", "slaves");
      if (!slaveFile.exists()) {
        throw new Exception("Cannot find slave file.");
      }
      String slaveFilePath = slaveFile.getAbsolutePath();
      InputStream is = new FileInputStream(slaveFilePath);
      hosts = Files.loadResourceFile(is);
    }
    return hosts;
  }

  public static String buildUUIDSuffix(String name) {
    return name + UUID.randomUUID();
  }

  public static String getCurrentYYYYMMDD() {
    Date date = new Date(System.currentTimeMillis());
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    String time = sdf.format(date);
    return time;
  }

  public static String getConfGlobal(String key, String... paths) {
    String dewHome = getDewHome();
    for (String path : paths) {
      File confFile = new File(dewHome, path);
      if (confFile.exists()) {
        try {
          Map<String, String> conf = Files.loadPropertiesFile(new FileInputStream(confFile));
          String value = conf.get(key);
          if (value != null) {
            return value;
          }
        } catch (Exception e) {
          System.out.println(e);
          e.printStackTrace();
        }
      }
    }
    return null;
  }
}
