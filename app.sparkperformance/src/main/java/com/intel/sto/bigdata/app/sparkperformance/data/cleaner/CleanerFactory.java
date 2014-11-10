package com.intel.sto.bigdata.app.sparkperformance.data.cleaner;

import com.intel.sto.bigdata.app.sparkperformance.Constants;
import com.intel.sto.bigdata.app.sparkperformance.WorkloadConf;

public class CleanerFactory {
  public static Cleaner getCleaner() {
    if (WorkloadConf.get(Constants.WORKLOAD_STEP_CLEANER).equals("linuxCleaner"))
      return new DstatCleaner(new DriverLogCleaner(new EmptyCleaner()));
    else if (WorkloadConf.get(Constants.WORKLOAD_STEP_CLEANER).equals("XCleaner"))
      return new XCleaner(new DriverLogCleaner(new EmptyCleaner()));
    else
      return new EmptyCleaner();
  }
}
