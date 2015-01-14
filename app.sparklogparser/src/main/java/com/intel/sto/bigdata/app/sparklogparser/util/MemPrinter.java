package com.intel.sto.bigdata.app.sparklogparser.util;

import com.intel.sto.bigdata.app.sparklogparser.common.MemInfo;
import com.intel.sto.bigdata.app.sparklogparser.common.rddMemInfo;
import com.intel.sto.bigdata.app.sparklogparser.model.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

public class MemPrinter {
  public static void print(App app, String path) throws Exception {

    for (Map.Entry<String, MemInfo> entry : app.getMemMap().entrySet()) {
      FileOutputStream rddMemFs =
          new FileOutputStream(new File(path + entry.getKey() , "rddmem.csv"));
      for (Map.Entry<String, rddMemInfo> rddEntry : entry.getValue().getRddMemInfoMap().entrySet()) {
        rddMemFs.write((rddEntry.getKey() + "," + rddEntry.getValue().getMemSize() + ","
            + (rddEntry.getValue().getTime() - app.getLastChild().getAppStartTime()) + "\r\n")
            .getBytes());
      }
      rddMemFs.close();
    }

  }
}
