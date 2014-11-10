package com.intel.sto.bigdata.app.sparkperformance.data.parser;

import java.util.ArrayList;
import java.util.List;

import com.intel.sto.bigdata.app.sparklogparser.DriverlogMain;
import com.intel.sto.bigdata.app.sparkperformance.Constants;
import com.intel.sto.bigdata.app.sparkperformance.Util;
import com.intel.sto.bigdata.app.sparkperformance.WorkloadConf;

public class XLogParser implements LogParser {

  @Override
  public void parse() throws Exception {
    // FIXME the same function as linux parser now
    DriverlogMain.processFile(Util.getWorkloadPath() + Util.getLogFileName());
    // copy csv files to workdir
    List<String> csvList = new ArrayList<String>();
    csvList.add(Constants.JOB_NAME + Constants.CSV_SUFFIX);
    csvList.add(Constants.STAGE_NAME + Constants.CSV_SUFFIX);
    csvList.add(Constants.TASK_NAME + Constants.CSV_SUFFIX);
    for (String csv : csvList) {
      String cp = "/bin/cp /tmp/" + csv + " " + WorkloadConf.get(Constants.WORKLOAD_OUTPUT_PATH);
      Util.runCmd(cp);
    }
  }

}
