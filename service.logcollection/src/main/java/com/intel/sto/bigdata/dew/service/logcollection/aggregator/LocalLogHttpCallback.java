package com.intel.sto.bigdata.dew.service.logcollection.aggregator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import com.intel.sto.bigdata.dew.http.server.HttpStreamCallback;
import com.intel.sto.bigdata.dew.service.logcollection.Constants;

public class LocalLogHttpCallback extends HttpStreamCallback {

  @Override
  public void call(Map<String, String> parameters, InputStream is) {
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      File outputPath = new File("/tmp", parameters.get(Constants.APP_ID));
      outputPath.mkdirs();
      String hostName = parameters.get(Constants.HOST_NAME);
      String executorId = parameters.get(Constants.EXECUTOR_ID);
      String logFileName = parameters.get(Constants.LOG_FILE_NAME);
      FileWriter fow =
          new FileWriter(new File(outputPath, hostName + "." + executorId + "." + logFileName));
      String line;
      while ((line = br.readLine()) != null) {
        fow.write(hostName + " " + executorId + " " + logFileName + " " + line
            + System.getProperty("line.separator"));
      }
      fow.close();
      br.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
