package com.intel.sto.bigdata.app.sparklogparser.util;

import com.intel.sto.bigdata.app.sparklogparser.model.App;
import com.intel.sto.bigdata.app.sparklogparser.model.Job;
import com.intel.sto.bigdata.app.sparklogparser.model.Stage;
import com.intel.sto.bigdata.app.sparklogparser.model.Task;
import com.intel.sto.bigdata.app.sparklogparser.model.TaskSet;

public class TimeAdjuster {

  public static Long appStartTime;
  public static Long appEndTime;

  public static void recordTime(String logLine) {
    if (logLine.matches("[0-9][0-9]/[0-1][0-9]/[0-3][0-9](.*)")) {
      try {
        long time = Util.transformTime(logLine.substring(0, 17));
        if (appStartTime == null) {
          appStartTime = time;
        }
        appEndTime = time;
      } catch (Exception e) {
        // Do nothing, just skip the line.
      }
    }
  }

  public static void adjustTime(App app) {
    app.setStartTime(appStartTime);
    app.setEndTime(appEndTime);
    for (Job job : app.getChildren()) {
      job.setAppStartTime(appStartTime);
      job.setAppEndTime(appEndTime);
      for (Stage stage : job.getChildren()) {
        stage.setAppStartTime(appStartTime);
        stage.setAppEndTime(appEndTime);
        for (TaskSet taskSet : stage.getChildren()) {
          for (Task task : taskSet.getChildren()) {
            task.setAppStartTime(appStartTime);
            task.setAppEndTime(appEndTime);
          }
        }
      }
    }
  }
}
