package com.intel.sto.bigdata.app.webcenter.logic.timer;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

public class Timer {
  static private Timer instance = new Timer();
  private Scheduler scheduler;

  private Timer() {
    SchedulerFactory sc = new StdSchedulerFactory();
    try {
      scheduler = sc.getScheduler();
      scheduler.start();
    } catch (SchedulerException e) {
      e.printStackTrace();
    }
  }

  public static Timer getInstance() {
    return instance;
  }

  public void schedule(String jobName, String cronExpression) {
    try {
      JobDetail jd = new JobDetail(jobName, TimerJob.class);
      jd.getJobDataMap().put("jobName", jobName);
      CronTrigger trigger = new CronTrigger(jobName);
      trigger.setCronExpression(cronExpression);
      scheduler.scheduleJob(jd, trigger);
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}