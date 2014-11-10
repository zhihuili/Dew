package com.intel.sto.bigdata.app.sparkperformance.run;

import java.io.File;

import com.intel.sto.bigdata.app.sparkperformance.Constants;
import com.intel.sto.bigdata.app.sparkperformance.Util;
import com.intel.sto.bigdata.app.sparkperformance.WorkloadConf;

public class LinuxRunner extends Runner {

  @Override
  public void run() throws Exception {
    String workloadPath = Util.getWorkloadPath();
    String drunCp =
        "/bin/cp " + WorkloadConf.get(Constants.WORKLOAD_WORKDIR) + "/script/drun_wl.sh "
            + workloadPath;
    Runtime runtime = Runtime.getRuntime();
    String[] cpCmd = { "/bin/sh", "-c", drunCp };
    System.out.println(drunCp);
    if (runtime.exec(cpCmd).waitFor() != 0) {
      throw new Exception("cp drun_wl.sh failed.");
    }

    String workloadConfRun =
        Constants.WORKLOAD_CONF_PREFIX + "." + WorkloadConf.get(Constants.WORKLOAD_NAME) + "."
            + Constants.WORKLOAD_RUN_SUFFIX;
    System.out.println(workloadConfRun);
    String logFile = Util.getLogFileName();
    String runsh = workloadPath + "drun_wl.sh " + WorkloadConf.get(workloadConfRun) + " " + logFile;
    String[] runCmd = { "/bin/sh", "-c", runsh };
    System.out.println(runsh);
    if (runtime.exec(runCmd, null, new File(workloadPath)).waitFor() != 0) {
      throw new Exception("drun failed.");
    }
  }
}
