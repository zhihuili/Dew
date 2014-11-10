package com.intel.sto.bigdata.app.sparklogparser.processor;

import com.intel.sto.bigdata.app.sparklogparser.model.App;

public class UserProcessor extends BaseProcessor {

  @Override
  public void process(App app) throws Exception {
    app.setUserName(super.subLogLine(8));
  }

}
