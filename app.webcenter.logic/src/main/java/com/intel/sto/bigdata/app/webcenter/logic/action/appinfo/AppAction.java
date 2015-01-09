package com.intel.sto.bigdata.app.webcenter.logic.action.appinfo;

import java.util.ArrayList;

import com.intel.sto.bigdata.app.webcenter.logic.action.bean.*;
import com.intel.sto.bigdata.app.webcenter.logic.db.DBOperator;
import com.opensymphony.xwork2.ActionSupport;

public class AppAction extends ActionSupport {
  private String appId;
  private AppBean app;
  private ArrayList<AppBean> apps;
  private String appName;
  private DBOperator operator = new DBOperator();

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

  public String getAppName() {
    return appName;
  }

  public void setAppName(String appName) {
    this.appName = appName;
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

  public String search() throws Exception {
    app = operator.getSingleAppByName(appName);
    apps = new ArrayList<AppBean>();
    apps.add(app);
    return SUCCESS;
  }
}