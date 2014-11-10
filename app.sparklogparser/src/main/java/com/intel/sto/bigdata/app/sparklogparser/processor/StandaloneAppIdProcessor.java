package com.intel.sto.bigdata.app.sparklogparser.processor;

import com.intel.sto.bigdata.app.sparklogparser.model.App;

public class StandaloneAppIdProcessor extends BaseProcessor {

  @Override
  public void process(App app) throws Exception {
    app.setAppId(super.subLogLine(11));

  }

}
