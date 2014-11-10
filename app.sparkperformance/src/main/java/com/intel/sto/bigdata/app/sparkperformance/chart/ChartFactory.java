package com.intel.sto.bigdata.app.sparkperformance.chart;

import com.intel.sto.bigdata.app.sparkperformance.Constants;
import com.intel.sto.bigdata.app.sparkperformance.WorkloadConf;

public class ChartFactory {
  public static SparkChart getChart() {
    if (WorkloadConf.get(Constants.WORKLOAD_STEP_CHART).equals("emptyChart")) {
      return new EmptyChart();
    }
    JobChart jc = new JSTChart();
    XChart xc = new XChart();
    xc.setCsvFolder(WorkloadConf.get(Constants.WORKLOAD_OUTPUT_PATH));
    xc.setJpgFolder(WorkloadConf.get(Constants.WORKLOAD_OUTPUT_PATH));
    xc.setJobChart(jc);
    return xc;
  }
}
