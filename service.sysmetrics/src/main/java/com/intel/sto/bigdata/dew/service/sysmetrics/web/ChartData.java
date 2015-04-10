package com.intel.sto.bigdata.dew.service.sysmetrics.web;

import java.util.List;

public class ChartData {
  private String name;
  private List data;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List getData() {
    return data;
  }

  public void setData(List data) {
    this.data = data;
  }

}
