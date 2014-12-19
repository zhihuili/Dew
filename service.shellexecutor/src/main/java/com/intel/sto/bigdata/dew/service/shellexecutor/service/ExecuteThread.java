package com.intel.sto.bigdata.dew.service.shellexecutor.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.intel.sto.bigdata.dew.http.client.HttpSimpleClient;
import com.intel.sto.bigdata.dew.http.client.HttpStreamClient;
import com.intel.sto.bigdata.dew.service.shellexecutor.message.ExecuteRequest;

public class ExecuteThread extends Thread {
  ExecuteRequest request;

  public ExecuteThread(ExecuteRequest request) {
    this.request = request;
  }

  @Override
  public void run() {
    Runtime runtime = Runtime.getRuntime();
    BufferedReader br = null;
    FileWriter fw = null;
    try {
      Process process =
          runtime.exec(request.getCommand(), request.getEnvp(), new File(request.getDirectory()));
      br = new BufferedReader(new InputStreamReader(process.getInputStream()));
      fw = new FileWriter("/tmp/" + request.getId());
      String line;
      while ((line = br.readLine()) != null) {
        fw.write(line + System.getProperty("line.separator"));
      }
      int exitValue = process.waitFor();
      if (request.getStatusUrl() != null && !request.getStatusUrl().trim().equals("")) {
        Map<String, String> status = new HashMap<String, String>();
        status.put("id", request.getId());
        if (exitValue == 0) {
          status.put("status", "failure");
        } else {
          status.put("status", "success");
        }
        HttpSimpleClient.get(request.getStatusUrl(), status);
        if (request.getLogUrl() != null) {
          Map<String, String> para = new HashMap<String, String>();
          para.put("id", request.getId());
          InputStream is = new FileInputStream("/tmp/" + request.getId());
          HttpStreamClient.post(request.getLogUrl(), para, is);
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (fw != null) {
        try {
          fw.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
