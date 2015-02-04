package com.intel.sto.bigdata.app.sparkreport.ui;

public class Report {

  public static void main(String[] args) {
    if (args.length < 1) {
      System.out.println("Usage:");
      System.out.println("report.sh targetDirectory");
      System.exit(1);
    }

    new Executor().execute(args[0]);
  }

}
