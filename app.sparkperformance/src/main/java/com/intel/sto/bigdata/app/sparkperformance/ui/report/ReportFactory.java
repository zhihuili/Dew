package com.intel.sto.bigdata.app.sparkperformance.ui.report;

import com.intel.sto.bigdata.app.sparkperformance.Constants;
import com.intel.sto.bigdata.app.sparkperformance.WorkloadConf;

public class ReportFactory {
  public static void getReport() {
    if (WorkloadConf.get(Constants.WORKLOAD_STEP_REPORT).equals("false")) {
      return;
    }
    (new HtmlReport()).buildReport();
  }
}
