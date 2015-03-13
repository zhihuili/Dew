package com.intel.sto.bigdata.app.sparkreport.process;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.intel.sto.bigdata.app.logmanager.LogCollection;
import com.intel.sto.bigdata.app.sparklogparser.model.App;
import com.intel.sto.bigdata.app.sparkpowermeter.OfflineExecutor;
import com.intel.sto.bigdata.app.sparkreport.utils.Util;
import com.intel.sto.bigdata.dew.conf.DewConf;

public class SparkLogProcessor {
  private static Logger log = Logger.getLogger(SparkLogProcessor.class);
  private static SparkLogProcessor instance = new SparkLogProcessor();

  public static SparkLogProcessor getInstance() {
    return instance;
  }

  public void process(File file) throws Exception {
    App currentApp = Util.parseSparkDriverLog(file);
    String appId = currentApp.getAppId();
    collectSparkLog(appId);
    runSparkPowerMeter(currentApp);

  }

  private void runSparkPowerMeter(App app) throws Exception {
    String dewHome = System.getenv("DEW_HOME");
    String confPath = (new File(dewHome, "app.sparkpowermeter/conf.properties")).getAbsolutePath();
    String desPath = (new File(dewHome, "app.sparkpowermeter/runner.des")).getAbsolutePath();

    OfflineExecutor.execute(confPath, desPath, app, new HashMap<String, String>());
  }

  private void collectSparkLog(String appId) throws Exception {
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

    String logPath = Util.loadConf().getProperty("spark.log.path");
    if (logPath != null) {
      logPathList.add(logPath);
    }
    log.info("collec yarn log " + appId + " from:" + logPathList);
    LogCollection.collect(appId, logPathList, DewConf.getDewConf().get("master"));
  }
}
