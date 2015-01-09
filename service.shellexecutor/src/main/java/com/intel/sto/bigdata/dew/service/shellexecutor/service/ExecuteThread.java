package com.intel.sto.bigdata.dew.service.shellexecutor.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.intel.sto.bigdata.dew.http.client.HttpSimpleClient;
import com.intel.sto.bigdata.dew.http.client.HttpStreamClient;
import com.intel.sto.bigdata.dew.service.shellexecutor.message.ExecuteRequest;
import com.intel.sto.bigdata.dew.utils.PrintStreamThread;

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
      fw = new FileWriter("/tmp/" + request.getId());
      new PrintStreamThread(process.getInputStream(), fw);
      new PrintStreamThread(process.getErrorStream(), fw);
      int exitValue = process.waitFor();
      if (fw != null) {
        fw.close();
      }
      if (request.getStatusUrl() != null && !request.getStatusUrl().trim().equals("")) {
        Map<String, String> status = new HashMap<String, String>();
        status.put("id", request.getId());
        if (exitValue != 0) {
          status.put("status", "failure");
        } else {
          status.put("status", "success");
        }
        if (request.getLogUrl() != null) {
          Map<String, String> para = new HashMap<String, String>();
          para.put("id", request.getId());
          InputStream is = new FileInputStream("/tmp/" + request.getId());
          HttpStreamClient.post(request.getLogUrl(), para, is);
        }
        HttpSimpleClient.get(request.getStatusUrl(), status);
      }

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
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
