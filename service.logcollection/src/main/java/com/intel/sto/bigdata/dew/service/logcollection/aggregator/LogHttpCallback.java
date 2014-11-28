package com.intel.sto.bigdata.dew.service.logcollection.aggregator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import com.intel.sto.bigdata.dew.http.server.HttpStreamCallback;
import com.intel.sto.bigdata.dew.service.logcollection.Constants;
import com.intel.sto.bigdata.dew.service.logcollection.SaveDstatFile;

public class LogHttpCallback extends HttpStreamCallback {
  private String path;

  public LogHttpCallback(String path) {
    this.path = path;
  }

  @Override
  public void call(Map<String, String> parameters, InputStream is) {
    try {
      String hostName = parameters.get(Constants.HOST_NAME);
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      String content = br.readLine();
      SaveDstatFile.save(path, hostName, content);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
