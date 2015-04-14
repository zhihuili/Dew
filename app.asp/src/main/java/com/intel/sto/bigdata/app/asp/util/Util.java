package com.intel.sto.bigdata.app.asp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

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
    execute(command, env, path, null,null);
  }

  public static void execute(String command, String[] env, String path, String logFile, Long timeout)
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
    int exitValue;
    if (timeout == null) {
      exitValue = process.waitFor();
    } else {
      exitValue = process.waitFor(timeout, TimeUnit.SECONDS) == true ? 0 : 1;
      process.destroyForcibly();
    }
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
    String dewHome = com.intel.sto.bigdata.dew.utils.Util.getDewHome();
    File aspConfFile = new File(dewHome + "/app.asp", "asp.conf");
    InputStream in = new FileInputStream(aspConfFile);
    Properties props = new Properties();
    props.load(in);
    in.close();
    return props;
  }

  public static Map<String, String> loadWorkload() throws Exception {
    String dewHome = com.intel.sto.bigdata.dew.utils.Util.getDewHome();
    File workloadFile = new File(dewHome + "/app.asp", "workload.conf");
    InputStream in = new FileInputStream(workloadFile);
    return Files.loadPropertiesFile(in);
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

  public static String buildOutputPath(Properties conf) {
    String output = conf.getProperty("output");
    String type = conf.getProperty("type");
    String plat = conf.getProperty("plat");
    String path = new File(output, plat + "." + type).getAbsolutePath();
    return path;
  }
}
