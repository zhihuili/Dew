package com.intel.sto.bigdata.app.asp.chart;

import java.util.Properties;

public class ChartProcessor {
  public void process(Properties conf) throws Exception {
    Data data = new Data();
    data.buildData(conf, "time");
    new Chart().draw(data);

    data = new Data();
    data.buildData(conf, "release");
    new Chart().draw(data);
  }
}
