package com.intel.sto.bigdata.app.sparkperformance.chart;

import java.util.List;

public class ChartSource {
  List<List<Double>> dataList;
  List<String> dataNameList = null;
  int freq;
  String chartName = "";
  String xAxisName = "time(s)";
  String yAxisName = "";
  double[][] marker;
  long remainder;

  // long startTime;

  public void setDataList(List<List<Double>> dataList) {
    this.dataList = dataList;
  }

  public void setDataNameList(List<String> dataNameList) {
    this.dataNameList = dataNameList;
  }

  public void setFreq(int freq) {
    this.freq = freq;
  }

  public void setChartName(String chartName) {
    this.chartName = chartName;
  }

  public void setXAxisName(String xAxisName) {
    this.xAxisName = xAxisName;
  }

  public void setYAxisName(String yAxisName) {
    this.yAxisName = yAxisName;
  }

  public void setMarker(double[][] marker) {
    this.marker = marker;
  }

  public void setRemainder(long remainder) {
    this.remainder = remainder;
  }

}
