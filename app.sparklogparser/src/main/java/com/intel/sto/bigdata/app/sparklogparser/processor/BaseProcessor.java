package com.intel.sto.bigdata.app.sparklogparser.processor;

import com.intel.sto.bigdata.app.sparklogparser.model.App;
import com.intel.sto.bigdata.app.sparklogparser.model.Job;
import com.intel.sto.bigdata.app.sparklogparser.model.Node;
import com.intel.sto.bigdata.app.sparklogparser.model.Stage;
import com.intel.sto.bigdata.app.sparklogparser.model.Task;
import com.intel.sto.bigdata.app.sparklogparser.model.TaskSet;
import com.intel.sto.bigdata.app.sparklogparser.util.Util;

public abstract class BaseProcessor implements Processor {

  String logLine;
  long time;

  public Processor apply(String logLine) throws Exception {
    this.logLine = logLine;
    time = getTime();
    return this;
  }

  private long getTime() throws Exception {
    return Util.transformTime(logLine.substring(0, 17));
  }

  public void initNode(Node node) {
    node.setStr(logLine);
  }

  String subLogLine(int... positions) {
    String[] strArray = logLine.split(" ");
    StringBuilder result = new StringBuilder();
    for (int i : positions) {
      result.append(strArray[i] + " ");
    }
    return result.toString().substring(0, result.length() - 1);
  }

  Stage findStage(App app, String stageId) {
    for (Job job : app.getChildren()) {
      for (Stage stage : job.getChildren()) {
        if (stage.getId().equals(stageId)) {
          return stage;
        }
      }
    }
    throw new RuntimeException("Can't find stage:" + stageId);
  }

  TaskSet findTaskSet(App app, String taskSetId) {
    for (Job job : app.getChildren()) {
      for (Stage stage : job.getChildren()) {
        for (TaskSet taskSet : stage.getChildren()) {
          if (taskSet.getId().equals(taskSetId)) {
            return taskSet;
          }
        }
      }
    }
    throw new RuntimeException("Can't find stage:" + taskSetId);
  }

  Task findTask(App app, String taskId) {
    for (Job job : app.getChildren()) {
      for (Stage stage : job.getChildren()) {
        for (TaskSet taskSet : stage.getChildren()) {
          for (Task task : taskSet.getChildren()) {
            if (task.getId().equals(taskId)) {
              return task;
            }
          }
        }
      }
    }
    throw new RuntimeException("Can't find stage:" + taskId);
  }

  Task findTask(App app, String stageId, String taskId) {
    for (Job job : app.getChildren()) {
      for (Stage stage : job.getChildren()) {
        if (stage.getId().equals(stageId)) {
          for (TaskSet taskSet : stage.getChildren()) {
            for (Task task : taskSet.getChildren()) {
              if (task.getId().equals(taskId)) {
                return task;
              }
            }
          }
        }
      }
    }
    throw new RuntimeException("Can't find stage:" + taskId);
  }

}
