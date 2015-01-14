package com.intel.sto.bigdata.app.sparklogparser.processor;

import com.intel.sto.bigdata.app.sparklogparser.model.App;

public class YarnAppIdProcessor extends BaseProcessor {

  @Override
  public void process(App app) throws Exception {
    app.setAppId(super.subLogLine(5));

  }

}
