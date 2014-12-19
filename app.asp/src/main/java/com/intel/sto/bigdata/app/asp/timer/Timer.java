package com.intel.sto.bigdata.app.asp.timer;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

public class Timer {
  SchedulerFactory sc = new StdSchedulerFactory();
  Scheduler scheduler;

  public void schedule(String cronExpression) {
    try {
      scheduler = sc.getScheduler();
      scheduler.start();
      JobDetail jd = new JobDetail("ASP", DailyJob.class);
      jd.getJobDataMap().put("type", "FULL");
      CronTrigger trigger = new CronTrigger("ASP");
      trigger.setCronExpression(cronExpression);
      scheduler.scheduleJob(jd, trigger);
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
