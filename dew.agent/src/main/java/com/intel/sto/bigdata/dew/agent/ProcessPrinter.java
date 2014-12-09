package com.intel.sto.bigdata.dew.agent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessPrinter extends Thread {
  Process process;

  public ProcessPrinter(Process process) {
    this.process = process;
  }

  @Override
  public void run() {
    BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
    String line;
    try {
      while ((line = br.readLine()) != null) {
        System.out.println(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
