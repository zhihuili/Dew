package com.intel.sto.bigdata.app.asp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.intel.sto.bigdata.app.asp.util.Util;

public class DataPrinter {

  public static String print(Properties conf, Map<String, Long> workresult) throws Exception {
    Date date = new Date(System.currentTimeMillis());
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    String time = sdf.format(date) + "_" + getCommitCode(conf);
    String output = Util.buildOutputPath(conf);
    FileWriter fw = null;
    try {
      fw = new FileWriter(new File(output, time));
      for (Entry<String, Long> entry : workresult.entrySet()) {
        fw.write(entry.getKey() + "=" + entry.getValue() + System.getProperty("line.separator"));
      }
    } finally {
      if (fw != null) {
        fw.close();
      }
    }
    return time;
  }

  private static String getCommitCode(Properties conf) throws IOException {
    String branch = conf.getProperty("branch");
    Runtime runtime = Runtime.getRuntime();
    Process process = runtime.exec("git log", null, new File(branch));
    java.io.InputStream is = process.getInputStream();
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    String line = br.readLine();
    br.close();
    process.destroy();
    if (line.startsWith("commit")) {
      return line.split(" ")[1].substring(0, 8);
    }
    return "00000000";

  }
}
