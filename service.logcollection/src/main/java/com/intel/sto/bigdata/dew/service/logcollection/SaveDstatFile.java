package com.intel.sto.bigdata.dew.service.logcollection;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveDstatFile {

  public static void save(String path, String hostName, String content) {

    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream(new File(path + "dstat_" + hostName + ".dat"));
      String[] contents = content.split(";");
      for (String line : contents) {
        fos.write((line + System.getProperty("line.separator")).getBytes());
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (fos != null) {
        try {
          fos.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
