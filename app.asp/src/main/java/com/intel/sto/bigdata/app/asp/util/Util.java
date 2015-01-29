package com.intel.sto.bigdata.app.asp.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import com.intel.sto.bigdata.dew.utils.Files;
import com.intel.sto.bigdata.dew.utils.PrintStreamThread;

public class Util {
  public static void printProcessLog(Process process) {
    new PrintStreamThread(process.getInputStream());
    new PrintStreamThread(process.getErrorStream());
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
      Util.printSplitLine("Successful:" + file.getAbsolutePath() + command);
      return;
    }
    Util.printSplitLine("Failed:" + file.getAbsolutePath() + command);
    throw new Exception("Failed:" + file.getAbsolutePath() + command);
  }

  public static Properties loadConf() throws Exception {
    InputStream in = ClassLoader.getSystemResourceAsStream("asp.conf");
    Properties props = new Properties();
    props.load(in);
    in.close();
    return props;
  }

  public static Map<String, String> loadWorkload() throws Exception {
    return Files.loadPropertiesFile("/workload.conf");
  }
}
