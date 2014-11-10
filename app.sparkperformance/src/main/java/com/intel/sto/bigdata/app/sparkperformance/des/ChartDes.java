package com.intel.sto.bigdata.app.sparkperformance.des;

import java.util.List;

public class ChartDes {

  String chartName;
  ChartProcessType processType = null;
  ChartType chartType;
  String yName = null;
  String groupName = null;
  String selectName = null;
  String selectValue = null;
  String aggregateName = null;
  List<String> colName;

  public void setChartName(String chartName) {
    this.chartName = chartName;
  }

  public void setYName(String yName) {
    this.yName = yName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public void setSelectName(String selectName) {
    this.selectName = selectName;
  }

  public void setSelectValue(String selectValue) {
    this.selectValue = selectValue;
  }

  public void setColName(List<String> colName) {
    this.colName = colName;
  }

  public void setChartProcessType(ChartProcessType processType) {
    this.processType = processType;
  }

  public void setChartType(ChartType chartType) {
    this.chartType = chartType;
  }

  public void setAggregateName(String aggregateName) {
    this.aggregateName = aggregateName;
  }

  public String getChartName() {
    return chartName;
  }

  public String getYName() {
    return yName;
  }

  public String getGroupName() {
    return groupName;
  }

  public ChartProcessType getChartProcessType() {
    return processType;
  }

  public ChartType getChartType() {
    return chartType;
  }

  public String getSelectName() {
    return selectName;
  }

  public String getSelectValue() {
    return selectValue;
  }

  public List<String> getColName() {
    return colName;
  }

  public String getAggregateName() {
    return aggregateName;
  }
}
