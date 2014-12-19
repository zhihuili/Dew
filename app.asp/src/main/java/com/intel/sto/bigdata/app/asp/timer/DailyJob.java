package com.intel.sto.bigdata.app.asp.timer;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.intel.sto.bigdata.app.asp.Executor;

public class DailyJob implements Job {

  @Override
  public void execute(JobExecutionContext arg0) throws JobExecutionException {
    try {
      Executor.execute();
    } catch (Exception e) {
      throw new JobExecutionException(e);
    }
  }

}
