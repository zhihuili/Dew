package com.intel.sto.bigdata.app.sparkperformance.data.parser;

import com.intel.sto.bigdata.app.sparkperformance.Constants;
import com.intel.sto.bigdata.app.sparkperformance.WorkloadConf;

public class LogParserFactory {
  public static LogParser getLogParser() {

    if (WorkloadConf.get(Constants.WORKLOAD_STEP_PARSER).equals("XParser")) {
      return new XLogParser();
    } else if (WorkloadConf.get(Constants.WORKLOAD_STEP_PARSER).equals("linuxParser"))
      return new DriverLogParser();
    else
      return new EmptyLogParser();
  }

}
