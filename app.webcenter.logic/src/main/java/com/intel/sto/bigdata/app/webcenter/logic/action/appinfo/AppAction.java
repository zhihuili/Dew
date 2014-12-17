package com.intel.sto.bigdata.app.webcenter.logic.action.appinfo;

import java.util.ArrayList;

import com.intel.sto.bigdata.app.webcenter.logic.action.bean.*;
import com.intel.sto.bigdata.app.webcenter.logic.db.DBOperator;
import com.opensymphony.xwork2.ActionSupport;

public class AppAction extends ActionSupport {
  public String app_id;
  public appBean app;
  public ArrayList<appBean> apps;
  public DBOperator operator = new DBOperator();

  public String getApp_id() {
    return app_id;
  }

  public void setApp_id(String app_id) {
    this.app_id = app_id;
  }

  public appBean getApp() {
    return app;
  }

  public void setApp(appBean app) {
    this.app = app;
  }

  public ArrayList<appBean> getApps() {
    return apps;
  }

  public void setApps(ArrayList<appBean> apps) {
    this.apps = apps;
  }

  public String list() throws Exception {
    apps = operator.getAllApp();
    return SUCCESS;
  }

  public String modify() throws Exception {
    operator.appModify(app);
    return SUCCESS;
  }

  public String load() throws Exception {
    app = operator.getSingleApp(app_id);
    return SUCCESS;
  }
}
