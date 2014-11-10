package com.intel.sto.bigdata.app.sparkperformance.data;

import com.intel.sto.bigdata.app.sparkperformance.Constants;
import com.intel.sto.bigdata.app.sparkperformance.WorkloadConf;

public class LoaderFactory {
  public static Loader getLoader() {
    if (WorkloadConf.get(Constants.WORKLOAD_STEP_LOADER).equals("linuxLoader"))
      return new LinuxLoader();
    else if (WorkloadConf.get(Constants.WORKLOAD_STEP_LOADER).equals("XLoader"))
      return new XLoader();
    else
      return new EmptyLoader();
  }
}
