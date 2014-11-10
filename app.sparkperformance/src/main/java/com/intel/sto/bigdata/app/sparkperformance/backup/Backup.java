package com.intel.sto.bigdata.app.sparkperformance.backup;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.intel.sto.bigdata.app.sparkperformance.Constants;
import com.intel.sto.bigdata.app.sparkperformance.WorkloadConf;

public class Backup {

  public static void backup() throws Exception {
    if (WorkloadConf.get(Constants.WORKLOAD_EXECUTE_BACKUP).equals("false")) {
      return;
    }

    Runtime run = Runtime.getRuntime();
    String backupName =
        WorkloadConf.get(Constants.WORKLOAD_NAME) + "_" + System.currentTimeMillis() + "/";
    File backupNameFile = new File(System.getProperty("user.dir") + "/BackupName");
    String nameInFile = getBackupName(backupNameFile);
    if (!nameInFile.equals("default") && !hasSameNameFloder(nameInFile)) {
      backupName = nameInFile;
    }
    String backupPath = WorkloadConf.get(Constants.WORKLOAD_BACKUP_PATH) + backupName;
    run.exec("mkdir " + backupPath).waitFor();
    String cmdS =
        "/bin/cp " + WorkloadConf.get(Constants.WORKLOAD_OUTPUT_PATH) + "*.* " + backupPath;
    String[] cmd = { "/bin/sh", "-c", cmdS };
    System.out.println(cmdS);
    Process p = run.exec(cmd);
    if (p.waitFor() != 0) {
      throw new Exception("backup failed.");
    }

    String webPath = System.getProperty("user.dir") + "/workload.web/src/main/webapp/LogPic";
    System.out.println(Runtime.getRuntime().exec("mkdir " + webPath).waitFor());
    String cmdT = "/bin/mv " + WorkloadConf.get(Constants.WORKLOAD_OUTPUT_PATH) + "*.* " + webPath;
    System.out.println(cmdT);
    String[] cmdW = { "/bin/sh", "-c", cmdT };
    p = Runtime.getRuntime().exec(cmdW);
    // clean the buffered area to avoid deadlock
    BufferedInputStream inErr = new BufferedInputStream(p.getErrorStream());
    BufferedReader br = new BufferedReader(new InputStreamReader(inErr));
    BufferedInputStream inInput = new BufferedInputStream(p.getInputStream());
    BufferedReader br1 = new BufferedReader(new InputStreamReader(inInput));
    String line = null;
    while ((line = br.readLine()) != null)
      while ((line = br1.readLine()) != null)
        if (p.waitFor() != 0) {
          throw new Exception("web copy failed.");
        }
  }

  public static String getBackupName(File path) throws IOException {
    String result = "default";

    if (!path.exists()) {
      return result;
    } else {
      FileReader reader = new FileReader(path);
      BufferedReader br = new BufferedReader(reader);
      String temp = null;
      while ((temp = br.readLine()) != null) {
        result = temp;
      }
      return result;
    }
  }

  /**
   * before backup, whether have same name floder
   * 
   * @param floderName backup floder name
   * @return
   */
  public static boolean hasSameNameFloder(String floderName) {
    boolean result = false;

    File floder = new File(WorkloadConf.get(Constants.WORKLOAD_BACKUP_PATH) + floderName);

    if (floder.exists()) {
      result = true;
    }

    return result;
  }
}
