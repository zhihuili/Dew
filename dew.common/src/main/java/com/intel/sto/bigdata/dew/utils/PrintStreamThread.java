package com.intel.sto.bigdata.dew.utils;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PrintStreamThread extends Thread {
  private InputStream is;
  private Printer printer;

  public PrintStreamThread(InputStream is) {
    this.is = is;
    printer = new Printer() {
      @Override
      public void print(String s) {
        System.out.println(s);
      }
    };
    this.start();
  }

  public PrintStreamThread(InputStream is, final FileWriter fw) {
    this.is = is;
    if (fw != null) {
      printer = new Printer() {
        @Override
        public void print(String s) throws IOException {
          // FIXME thread safety
          fw.write(s + System.getProperty("line.separator"));
        }
      };
    }
    this.setDaemon(true);
    this.start();
  }

  @Override
  public void run() {
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    String line;
    try {
      while ((line = br.readLine()) != null) {
        if (printer != null) {
          printer.print(line);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

interface Printer {
  void print(String s) throws IOException;
}
