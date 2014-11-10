package com.intel.sto.bigdata.app.sparklogparser.processor;

import com.intel.sto.bigdata.app.sparklogparser.model.App;
import com.intel.sto.bigdata.app.sparklogparser.model.Node;

public class TaskSetAddProcessor extends BaseProcessor {

  @Override
  public void process(App app) throws Exception {
    Node taskSet = app.getContext().get(app.CURRENT_TASK_SET);
    taskSet.setId(super.subLogLine(7));
  }

}
