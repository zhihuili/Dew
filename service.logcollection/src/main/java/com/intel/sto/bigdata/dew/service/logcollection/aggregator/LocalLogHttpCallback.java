package com.intel.sto.bigdata.dew.service.logcollection.aggregator;

import java.io.BufferedReader;
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
      String hostName = parameters.get(Constants.HOST_NAME);
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      String content = br.readLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
