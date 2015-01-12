package com.intel.sto.bigdata.app.webcenter.logic.action.job;

import java.util.ArrayList;

import com.opensymphony.xwork2.ActionSupport;
import com.intel.sto.bigdata.app.webcenter.logic.action.bean.*;
import com.intel.sto.bigdata.app.webcenter.logic.db.DBOperator;

public class JobRecordAction extends ActionSupport {
  private ArrayList<JobRecordBean> jobRecords;
  private DBOperator operator = new DBOperator();

  public ArrayList<JobRecordBean> getJobRecords() {
    return jobRecords;
  }

  public void setJobRecords(ArrayList<JobRecordBean> jobRecords) {
    this.jobRecords = jobRecords;
  }

  public String list() throws Exception {
    jobRecords = operator.getAllJobRecord();
    return SUCCESS;
  }
}
