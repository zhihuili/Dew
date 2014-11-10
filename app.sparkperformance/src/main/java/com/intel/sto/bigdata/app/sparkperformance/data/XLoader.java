package com.intel.sto.bigdata.app.sparkperformance.data;

import com.intel.sto.bigdata.app.sparkperformance.Util;

public class XLoader extends Loader {

  @Override
  public void loadData() throws Exception {
    // FIXME SAME AS linuxLoader
    String cmd =
        "/bin/cp " + Util.getWorkloadPath() + "*.dat " + Util.getWorkloadPath() + "*.log "
            + Util.getWorkPath();
    System.out.println(cmd);
    Util.runCmd(cmd);
  }
}
