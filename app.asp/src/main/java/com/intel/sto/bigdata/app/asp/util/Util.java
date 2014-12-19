package com.intel.sto.bigdata.app.asp.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import com.intel.sto.bigdata.dew.utils.Files;

public class Util {
  public static void printProcessLog(Process process) {
    BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
    String line;
    try {
      while ((line = br.readLine()) != null) {
        System.out.println(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void printSplitLine(String comments) {
    System.out.println("**************************************************************");
    System.out.println(comments);
    System.out.println("**************************************************************");
  }

  public static void execute(String command, String[] env, String path) throws Exception {

    Runtime runtime = Runtime.getRuntime();
    File file = null;
    if (path != null) {
      file = new File(path);
    }
    Process process = runtime.exec(command, env, file);
    Util.printProcessLog(process);
    int exitValue = process.waitFor();
    if (exitValue == 0) {
      Util.printSplitLine("Successful:" + command);
      return;
    }
    Util.printSplitLine("Failed:" + command);
    throw new Exception("Failed:" + command);
  }

  public static Map<String, String> loadConf() throws Exception {
    return Files.loadPropertiesFile("/asp.conf");
  }

  public static Map<String, String> loadWorkload() throws Exception {
    return Files.loadPropertiesFile("/workload.conf");
  }
}
