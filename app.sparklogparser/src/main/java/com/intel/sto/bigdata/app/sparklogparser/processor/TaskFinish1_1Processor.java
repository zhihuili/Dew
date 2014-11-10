package com.intel.sto.bigdata.app.sparklogparser.processor;

import com.intel.sto.bigdata.app.sparklogparser.model.App;
import com.intel.sto.bigdata.app.sparklogparser.model.Task;

public class TaskFinish1_1Processor extends BaseProcessor {

  @Override
  public void process(App app) throws Exception {
    String taskId = super.subLogLine(6);
    String stageId = super.subLogLine(9).split("\\.")[0];
    Task task = super.findTask(app, stageId, taskId);
    task.setEndTime(time);
    task.setDuration(super.subLogLine(8));
  }

}
