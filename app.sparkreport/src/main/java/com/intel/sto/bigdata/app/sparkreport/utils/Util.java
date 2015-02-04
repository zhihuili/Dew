package com.intel.sto.bigdata.app.sparkreport.utils;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import com.intel.sto.bigdata.app.sparklogparser.DriverlogMain;
import com.intel.sto.bigdata.app.sparklogparser.model.App;

public class Util {
  public static Properties loadConf() throws Exception {
    InputStream in = ClassLoader.getSystemResourceAsStream("report.conf");
    Properties props = new Properties();
    props.load(in);
    in.close();
    return props;
  }
  
  public static App parseSparkDriverLog(File file) throws Exception {
    App app = DriverlogMain.parseLogFile(file.getAbsolutePath());
    return app;
  }
}
