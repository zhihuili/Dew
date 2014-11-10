package com.intel.sto.bigdata.app.sparkperformance.backup;

import java.io.File;

import com.intel.sto.bigdata.app.sparkperformance.Constants;
import com.intel.sto.bigdata.app.sparkperformance.WorkloadConf;

public class Init {

  public static void init() {
    File workPath = new File(WorkloadConf.get(Constants.WORKLOAD_OUTPUT_PATH));
    if (!workPath.exists()) {
      workPath.mkdir();
    }

  }
}
