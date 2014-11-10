package com.intel.sto.bigdata.app.sparklogparser.processor;

import com.intel.sto.bigdata.app.sparklogparser.model.App;
import com.intel.sto.bigdata.app.sparklogparser.model.Node;

public class JobFinishProcessor extends BaseProcessor {

  @Override
  public void process(App app) throws Exception {
    Node job = app.getContext().get(app.CURRENT_JOB);
    job.setEndTime(time);
    job.setDuration(super.subLogLine(10));
  }

}
