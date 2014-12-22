package com.intel.sto.bigdata.app.webcenter.logic.action.job;

import java.util.ArrayList;

import com.intel.sto.bigdata.app.webcenter.logic.action.bean.*;
import com.intel.sto.bigdata.app.webcenter.logic.db.DBOperator;
import com.opensymphony.xwork2.ActionSupport;

public class JobAction extends ActionSupport {
  public String jobId;
  public jobBean job;
  public ArrayList<jobBean> jobs;
  public DBOperator operator = new DBOperator();

  public String getJobId() {
    return jobId;
  }

  public void setJobId(String jobId) {
    this.jobId = jobId;
  }

  public jobBean getJob() {
    return job;
  }

  public void setJob(jobBean job) {
    this.job = job;
  }

  public ArrayList<jobBean> getJobs() {
    return jobs;
  }

  public void setJobs(ArrayList<jobBean> jobs) {
    this.jobs = jobs;
  }

  public String list() throws Exception {
    jobs = operator.getAllJob();
    return SUCCESS;
  }

  public String modify() throws Exception {
    operator.jobModify(job);
    return SUCCESS;
  }

  public String load() throws Exception {
    job = operator.getSingleJob(jobId);
    return SUCCESS;
  }
}
