package com.intel.sto.bigdata.app.webcenter.logic.util;

import java.io.File;
import java.io.IOException;

import com.intel.sto.bigdata.app.sparklogparser.DriverlogMain;
import com.intel.sto.bigdata.app.sparklogparser.model.App;
import com.intel.sto.bigdata.app.sparkperformance.Util;
import com.intel.sto.bigdata.app.sparkpowermeter.OfflineExecutor;
import com.intel.sto.bigdata.app.webcenter.logic.Constants;
import com.intel.sto.bigdata.app.webcenter.logic.WebCenterContext;

public class Utils {

  public static App parseSparkDriverLog(String uuid) throws Exception {
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
