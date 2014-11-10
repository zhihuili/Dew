package com.intel.sto.bigdata.app.sparklogparser.processor;

import com.intel.sto.bigdata.app.sparklogparser.model.App;
import com.intel.sto.bigdata.app.sparklogparser.model.Task;

public class TaskFinishProcessor extends BaseProcessor {

  @Override
  public void process(App app) throws Exception {
    String taskId = super.subLogLine(6);
    Task task = super.findTask(app, taskId);
    task.setEndTime(time);
    task.setDuration(super.subLogLine(8));
  }

}
