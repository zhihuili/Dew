package com.intel.sto.bigdata.app.sparkperformance.ui;

public class Cli {

  public static void main(String[] args) {
    if (args.length < 3) {
      System.err.println("Please input workload name");
      System.exit(1);
    }
    Executor exe = new Executor();
    try {
      exe.execute(args);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
