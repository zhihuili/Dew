package com.intel.sto.bigdata.app.sparkreport.report;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;

import com.intel.sto.bigdata.app.sparklogparser.model.App;
import com.intel.sto.bigdata.app.sparkreport.utils.Util;

public class SparkResultBuilder {
  private static SparkResultBuilder instance = new SparkResultBuilder();

  public static SparkResultBuilder getInstance() {
    return instance;
  }

  public Result build(File file) throws Exception {
    Result result = new Result();
    result.setAppName(file.getName());
    App app = Util.parseSparkDriverLog(file);
    if (app == null) {
      return result;
    }
    result.setAppId(app.getAppId());
    if (app.getStartTime() != 0) {
      result.setStartTime(new Date(app.getStartTime()).toString());
    }
    if (app.getEndTime() != 0) {
      result.setEndTime(new Date(app.getEndTime()).toString());
    }
    if (app.getDuration() != null) {
      result.setDuration(app.getDuration());
    }
    result.setExcepitonMessage(findException(file));
    return result;
  }

  private String findException(File file) throws Exception {
    String result = null;
    BufferedReader br = new BufferedReader(new FileReader(file));
    String line;
    while ((line = br.readLine()) != null) {
      if (line.startsWith("Exception")) {
        result = line;
      }
    }
    br.close();
    return result;
  }
}
