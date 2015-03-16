package com.intel.sto.bigdata.app.asp.chart;

import java.util.Properties;

public class ChartProcessor {
  public void process(Properties conf) throws Exception {
    Vm vm = new Vm();
    Data data = new Data();
    data.buildData(conf, "time");
    new Chart().draw(data);
    vm.createHtml(data, "./WEB/result_" + data.getType() + ".vm", conf);
    vm.createHtml(data, "./WEB/detail.vm", conf);

    data = new Data();
    data.buildData(conf, "release");
    new Chart().draw(data);
    vm.createHtml(data, "./WEB/result_" + data.getType() + ".vm", conf);

    vm.createHtml(data, "./WEB/about.vm", conf);
    vm.createHtml(data, "./WEB/index.vm", conf);
    vm.createHtml(data, "./WEB/result.vm", conf);
    vm.createHtml(data, "./WEB/" + data.getPlat() + ".vm", conf);
    Git.push(conf);
  }
}
