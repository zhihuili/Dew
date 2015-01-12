package com.intel.sto.bigdata.app.webcenter.logic.action.appinfo;

import java.util.ArrayList;

import com.intel.sto.bigdata.app.webcenter.logic.action.bean.*;
import com.intel.sto.bigdata.app.webcenter.logic.db.DBOperator;
import com.opensymphony.xwork2.ActionSupport;

public class AppRecordAction extends ActionSupport {
  private ArrayList<AppRecordBean> appRecords;
  private DBOperator operator = new DBOperator();

  public ArrayList<AppRecordBean> getAppRecords() {
    return appRecords;
  }

  public void setAppRecords(ArrayList<AppRecordBean> appRecords) {
    this.appRecords = appRecords;
  }

  public String list() throws Exception {
    appRecords = operator.getAllAppRecord();
    return SUCCESS;
  }
}
