package com.intel.sto.bigdata.app.sparklogparser.processor;

import com.intel.sto.bigdata.app.sparklogparser.model.App;
import com.intel.sto.bigdata.app.sparklogparser.model.Task;
import com.intel.sto.bigdata.app.sparklogparser.model.TaskSet;

public class TaskStart1_1Processor extends BaseProcessor {

  @Override
  public void process(App app) throws Exception {
    String stageId = super.subLogLine(9).split("\\.")[0];
    TaskSet taskSet = super.findStage(app, stageId).getFirstChild();
    Task task = new Task();
    taskSet.addNewChild(task);
    task.setStartTime(time);
    task.setId(super.subLogLine(6));
  }

}
