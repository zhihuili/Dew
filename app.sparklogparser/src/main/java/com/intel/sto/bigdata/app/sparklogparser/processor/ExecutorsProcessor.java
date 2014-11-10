package com.intel.sto.bigdata.app.sparklogparser.processor;

import com.intel.sto.bigdata.app.sparklogparser.model.App;

public class ExecutorsProcessor extends BaseProcessor {

  @Override
  public void process(App app) throws Exception {
    app.addNode(super.subLogLine(6).split("@")[1].split(":")[0]);
  }

}
