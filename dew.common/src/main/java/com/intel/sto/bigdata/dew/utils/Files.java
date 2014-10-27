package com.intel.sto.bigdata.dew.utils;

import java.io.File;

public class Files {

  private Files() {
  }

  public static File getDstatDataPath() {
    File dir = new File(".");
    try {
      File result = new File(dir.getCanonicalFile(), "data/dstat");
      if (!result.exists()) {
        result.mkdirs();
      }
      return result;
    } catch (Exception e) {
      return null;
    }
  }

  public static void main(String[] args) {
    System.out.println(getDstatDataPath());
  }
}
