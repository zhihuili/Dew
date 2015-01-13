package com.intel.sto.bigdata.app.sparklogparser.util;

import java.io.File;
import java.io.FileOutputStream;

import com.intel.sto.bigdata.app.sparklogparser.model.App;
import com.intel.sto.bigdata.app.sparklogparser.model.Job;
import com.intel.sto.bigdata.app.sparklogparser.model.Node;
import com.intel.sto.bigdata.app.sparklogparser.model.Stage;
import com.intel.sto.bigdata.app.sparklogparser.model.Task;
import com.intel.sto.bigdata.app.sparklogparser.model.TaskSet;

public class NodePrinter {

  public static void print(App app, String path) throws Exception {
    FileOutputStream jobFs = new FileOutputStream(new File(path , "job.csv"));
    FileOutputStream stageFs = new FileOutputStream(new File(path , "stage.csv"));
    FileOutputStream taskFs = new FileOutputStream(new File(path , "task.csv"));

    for (Job job : app.getChildren()) {
      String jobName = job.getName();
      jobFs.write((jobName + "," + buildTimeStr(job) + "\r\n").getBytes());
      for (Stage stage : job.getChildren()) {
        String stageName = stage.getName();
        stageFs.write((stageName + "," + jobName + "," + buildTimeStr(stage) + "\r\n").getBytes());
        for (TaskSet taskSet : stage.getChildren()) {
          for (Task task : taskSet.getChildren()) {
            taskFs
                .write((task.getId() + "," + stageName + "," + jobName + "," + buildTimeStr(task) + "\r\n")
                    .getBytes());
          }
        }
      }
    }
    jobFs.close();
    stageFs.close();
    taskFs.close();
  }

  private static String buildTimeStr(Node node) {
    return node.getStartTime() + "," + node.getEndTime() + ","
        + (node.getStartTime() - node.getAppStartTime()) + ","
        + (node.getEndTime() - node.getStartTime()) + ","
        + (node.getAppEndTime() - node.getEndTime());
  }
}
