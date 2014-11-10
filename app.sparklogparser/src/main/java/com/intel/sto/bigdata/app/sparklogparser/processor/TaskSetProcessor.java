package com.intel.sto.bigdata.app.sparklogparser.processor;

import com.intel.sto.bigdata.app.sparklogparser.model.App;
import com.intel.sto.bigdata.app.sparklogparser.model.TaskSet;

public class TaskSetProcessor extends BaseProcessor {

  @Override
  public void process(App app) throws Exception {
    String stageId = super.subLogLine(10);
    TaskSet taskSet = new TaskSet();
    super.findStage(app, stageId).addNewChild(taskSet);
    app.getContext().put(app.CURRENT_TASK_SET, taskSet);
  }

}
