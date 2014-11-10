package com.intel.sto.bigdata.app.sparkperformance.data;

/**
 * cp and scp all log & matrics files to local work folder.
 * 
 */
public abstract class Loader {
  public abstract void loadData() throws Exception;
}
