package com.intel.sto.bigdata.dew.agent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessPrinter extends Thread {
  Process process;
  String thisUrl;
  int hashcode;

  public ProcessPrinter(String thisUrl, Process process) {
    this.process = process;
    this.thisUrl = thisUrl;
    this.hashcode = process.hashCode();
  }

  @Override
  public void run() {
    BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
    String line;
    try {
      while ((line = br.readLine()) != null) {
        System.out.println(thisUrl + "-" + hashcode + " : " + line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
