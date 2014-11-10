package com.intel.sto.bigdata.app.sparklogparser.processor;

import com.intel.sto.bigdata.app.sparklogparser.model.App;

public interface Processor {

  void process(App app) throws Exception;

  Processor apply(String logLine) throws Exception;
}
