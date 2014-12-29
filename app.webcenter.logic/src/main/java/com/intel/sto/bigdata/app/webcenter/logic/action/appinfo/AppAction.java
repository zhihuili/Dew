package com.intel.sto.bigdata.app.webcenter.logic.action.appinfo;

import java.util.ArrayList;

import com.intel.sto.bigdata.app.webcenter.logic.action.bean.*;
import com.intel.sto.bigdata.app.webcenter.logic.db.DBOperator;
import com.opensymphony.xwork2.ActionSupport;

public class AppAction extends ActionSupport {
  public String appId;
  public AppBean app;
  public ArrayList<AppBean> apps;
  public DBOperator operator = new DBOperator();

  public String getAppId() {
    return appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public AppBean getApp() {
    return app;
  }

  public void setApp(AppBean app) {
    this.app = app;
  }

  public ArrayList<AppBean> getApps() {
    return apps;
  }

  public void setApps(ArrayList<AppBean> apps) {
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
    app = operator.getSingleApp(appId);
    return SUCCESS;
  }
}