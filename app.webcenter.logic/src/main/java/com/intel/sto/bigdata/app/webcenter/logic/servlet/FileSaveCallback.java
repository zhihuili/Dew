package com.intel.sto.bigdata.app.webcenter.logic.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import com.intel.sto.bigdata.app.webcenter.logic.WebCenterContext;
import com.intel.sto.bigdata.dew.http.server.HttpStreamCallback;

public class FileSaveCallback extends HttpStreamCallback {

  @Override
  public void call(Map<String, String> parameters, InputStream is) {
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      File outputPath = new File(WebCenterContext.getConf().get("dew.webcenter.applogfile.path"));
      if (!outputPath.exists()) {
        outputPath.mkdirs();
      }
      FileWriter fow = new FileWriter(new File(outputPath, parameters.get("id")));
      String line;
      while ((line = br.readLine()) != null) {
        fow.write(line + System.getProperty("line.separator"));
      }
      fow.close();
      br.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}