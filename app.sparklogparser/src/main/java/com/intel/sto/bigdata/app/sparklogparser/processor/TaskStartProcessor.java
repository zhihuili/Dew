package com.intel.sto.bigdata.app.sparklogparser.processor;

import com.intel.sto.bigdata.app.sparklogparser.model.App;
import com.intel.sto.bigdata.app.sparklogparser.model.Task;
import com.intel.sto.bigdata.app.sparklogparser.model.TaskSet;

public class TaskStartProcessor extends BaseProcessor {

  @Override
  public void process(App app) throws Exception {
    String taskSetId = super.subLogLine(6).split(":")[0];
    TaskSet taskSet = super.findTaskSet(app, taskSetId);
    Task task = new Task();
    taskSet.addNewChild(task);
    task.setStartTime(time);
    task.setId(super.subLogLine(9));
  }

}
