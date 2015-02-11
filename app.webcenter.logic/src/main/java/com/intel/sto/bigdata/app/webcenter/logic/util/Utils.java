package com.intel.sto.bigdata.app.webcenter.logic.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.intel.sto.bigdata.app.logmanager.LogCollection;
import com.intel.sto.bigdata.app.sparklogparser.DriverlogMain;
import com.intel.sto.bigdata.app.sparklogparser.model.App;
import com.intel.sto.bigdata.app.sparkperformance.Util;
import com.intel.sto.bigdata.app.sparkpowermeter.OfflineExecutor;
import com.intel.sto.bigdata.app.webcenter.logic.Constants;
import com.intel.sto.bigdata.app.webcenter.logic.WebCenterContext;

public class Utils {

  public static App parseSparkDriverLog(String uuid) {
    String path = WebCenterContext.getConf().get(Constants.SPARK_LOG_PATH);
    App app = DriverlogMain.parseLogFile((new File(path, uuid)).getAbsolutePath());
    return app;
  }

  public static void runSparkPowerMeter(App app) throws Exception {
    String dewHome = System.getenv("DEW_HOME");
    String confPath = (new File(dewHome, "app.sparkpowermeter/conf.properties")).getAbsolutePath();
    String desPath = (new File(dewHome, "app.sparkpowermeter/runner.des")).getAbsolutePath();

    OfflineExecutor.execute(confPath, desPath, app, WebCenterContext.getConf());
  }

  public static void collectSparkLog(String appId) throws Exception {
    List<String> logPathList = new ArrayList<String>();

    String sparkHome = System.getenv("SPARK_HOME");
    if (sparkHome != null) {
      logPathList.add(new File(sparkHome, "work").getAbsolutePath());
    }

    String yarnHome = System.getenv("HADOOP_HOME");
    if (yarnHome != null) {
      logPathList.add(new File(yarnHome, "logs/userlogs/").getAbsolutePath());
    }

    String yarnConfDir = System.getenv("YARN_CONF_DIR");
    if (yarnConfDir != null) {
      logPathList.add(new File(yarnConfDir, "../../logs/userlogs/").getAbsolutePath());
    }

    String logPath = WebCenterContext.getConf().get("spark.log.path");
    if (logPath != null) {
      logPathList.add(logPath);
    }

    LogCollection.collect(appId, logPathList, WebCenterContext.getConf().get(Constants.DEW_MASTER));
  }

  public static String getWorkloadPath() throws IOException {
    String backupPath = WebCenterContext.getConf().get(Constants.WORKLOAD_OUTPATH_PATH);
    // look for the path from app.sparkpowermeter
    if (backupPath == null) {
      File sparkPowerMeterConfFile =
          new File(System.getenv("DEW_HOME"), "app.sparkpowermeter/conf.properties");
      backupPath =
          Util.buildProperties(sparkPowerMeterConfFile.getAbsolutePath()).getProperty(
              Constants.WORKLOAD_OUTPATH_PATH);
    }
    return backupPath;
  }
}
