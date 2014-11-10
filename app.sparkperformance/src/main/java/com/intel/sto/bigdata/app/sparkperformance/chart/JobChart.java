package com.intel.sto.bigdata.app.sparkperformance.chart;

public interface JobChart {
  void draw() throws Exception;

  Long getStartTime();

  double[][] getMarker();

  void setCsvFolder(String csvFolder);

  void setJpgFolder(String jpgFolder);
}
