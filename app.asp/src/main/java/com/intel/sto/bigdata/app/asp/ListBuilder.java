package com.intel.sto.bigdata.app.asp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import com.intel.sto.bigdata.app.asp.util.Util;

public class ListBuilder {

  public static void buildList(Properties conf, int max, String newLine) throws Exception {
    String path = Util.buildOutputPath(conf);
    File listFile = new File(path, "list");
    List<String> list = new LinkedList<String>();
    if (listFile.exists()) {
      BufferedReader br = null;
      try {
        br = new BufferedReader(new FileReader(listFile));
        String line;
        while ((line = br.readLine()) != null) {
          list.add(line);
        }
      } finally {
        if (br != null) {
          br.close();
          listFile.delete();
        }
      }
    }
    if (!list.isEmpty()) {
      if (!list.get(list.size() - 1).equals(newLine)) {
        while (list.size() >= max) {
          list.remove(0);
        }
        list.add(newLine);
      }
    } else {
      list.add(newLine);
    }
    FileWriter fw = new FileWriter(listFile);
    for (String l : list) {
      fw.write(l + System.getProperty("line.separator"));
    }
    fw.close();
  }
}
