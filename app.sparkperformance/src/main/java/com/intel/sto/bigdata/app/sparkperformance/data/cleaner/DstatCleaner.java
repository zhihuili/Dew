package com.intel.sto.bigdata.app.sparkperformance.data.cleaner;

/**
 * Sum dstat.csv data from all slave nodes. <br>
 * Format data's unit(G memory, MB disk & network)
 * 
 */
public class DstatCleaner extends Cleaner {
  Cleaner cleaner;

  public DstatCleaner(Cleaner cleaner) {
    this.cleaner = cleaner;
  }

  @Override
  public void clean() throws Exception {
    this.cleaner.clean();
  }

}
