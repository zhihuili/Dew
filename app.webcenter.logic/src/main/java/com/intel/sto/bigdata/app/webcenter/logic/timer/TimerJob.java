package com.intel.sto.bigdata.app.webcenter.logic.timer;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.intel.sto.bigdata.app.webcenter.logic.action.run.RunJobAction;

public class TimerJob implements Job {

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    try {
      String jobName = context.getJobDetail().getJobDataMap().getString("jobName");
      RunJobAction action = new RunJobAction();
      action.setName(jobName);
      action.execute();
    } catch (Exception e) {
      throw new JobExecutionException(e);
    }
  }

}