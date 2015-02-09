package com.intel.sto.bigdata.app.asp.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import com.intel.sto.bigdata.dew.utils.Files;
import com.intel.sto.bigdata.dew.utils.PrintStreamThread;

public class Util {
  private static void printProcessLog(Process process) {
    new PrintStreamThread(process.getInputStream());
    new PrintStreamThread(process.getErrorStream());
  }

  private static void printProcessLogFile(Process process, FileWriter fw) throws IOException {
    new PrintStreamThread(process.getInputStream(), fw);
    new PrintStreamThread(process.getErrorStream(), fw);
  }

  public static void printSplitLine(String comments) {
    System.out.println("**************************************************************");
    System.out.println(comments);
    System.out.println("**************************************************************");
  }

  public static void execute(String command, String[] env, String path) throws Exception {
    execute(command, env, path, null);
  }

  public static void execute(String command, String[] env, String path, String logFile)
      throws Exception {
    FileWriter fw = null;
    Runtime runtime = Runtime.getRuntime();
    File file = null;
    if (path != null) {
      file = new File(path);
    }
    Process process = runtime.exec(command, env, file);
    if (logFile == null) {
      Util.printProcessLog(process);
    } else {
      fw = new FileWriter(logFile);
      printProcessLogFile(process, fw);
    }
    int exitValue = process.waitFor();
    if (fw != null) {
      fw.close();
    }
    if (exitValue == 0) {
      Util.printSplitLine("Successful:" + file == null ? "" : file.getAbsolutePath() + command);
      return;
    }
    Util.printSplitLine("Failed:" + file == null ? "" : file.getAbsolutePath() + command);
    throw new Exception("Failed:" + file == null ? "" : file.getAbsolutePath() + command);
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

  public static String buildLogPath() {
    String time = com.intel.sto.bigdata.dew.utils.Util.getCurrentYYYYMMDD();
    String dewHome = com.intel.sto.bigdata.dew.utils.Util.getDewHome();
    String logPath = dewHome + "/app.asp/logs/" + time;
    File logPathFile = new File(logPath);
    if (!logPathFile.exists()) {
      logPathFile.mkdirs();
    }
    return logPathFile.getAbsolutePath();
  }

  public static String buildLogFilePath(String name) {
    return buildLogPath() + "/" + name;
  }
}
