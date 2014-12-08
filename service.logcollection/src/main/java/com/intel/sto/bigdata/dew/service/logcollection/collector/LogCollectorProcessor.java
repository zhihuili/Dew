package com.intel.sto.bigdata.dew.service.logcollection.collector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.intel.sto.bigdata.dew.http.client.HttpStreamClient;
import com.intel.sto.bigdata.dew.service.logcollection.Constants;
import com.intel.sto.bigdata.dew.service.logcollection.message.LogCollectionRequest;
import com.intel.sto.bigdata.dew.utils.Host;

public class LogCollectorProcessor extends Thread {
  LogCollectionRequest request;

  public LogCollectorProcessor(LogCollectionRequest request) {
    this.request = request;
  }

  @Override
  public void run() {
    List<String> logPaths = request.getLogPathList();
    File path = null;
    for (String logPath : logPaths) {
      path = new File(logPath, request.getAppId());
      if (path.exists()) {
        break;
      }
    }
    if (path == null || !path.exists()) {
      return;
    }
    File[] executorPaths = path.listFiles();
    for (File executorPath : executorPaths) {
      if (executorPath.isDirectory()) {
        File[] logFiles = executorPath.listFiles();
        for (File logFile : logFiles) {
          if (logFile.isFile()) {
            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put(Constants.APP_ID, request.getAppId());
            parameters.put(Constants.EXECUTOR_ID, executorPath.getName());
            parameters.put(Constants.HOST_NAME, Host.getName());
            parameters.put(Constants.LOG_FILE_NAME, logFile.getName());
            try {
              InputStream is = new FileInputStream(logFile);
              HttpStreamClient.post(request.getHttpUrl(), parameters, is);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        }
      }
    }
  }
}
