package com.intel.sto.bigdata.app.asp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PrintStreamThread extends Thread {
  private InputStream is;

  public PrintStreamThread(InputStream is) {
    this.is = is;
    this.start();
  }

  @Override
  public void run() {
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
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
