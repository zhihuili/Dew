package com.intel.sto.bigdata.app.webcenter.logic.action.job;

import com.intel.sto.bigdata.app.webcenter.logic.db.DBOperator;
import com.intel.sto.bigdata.app.webcenter.logic.timer.Timer;
import com.opensymphony.xwork2.ActionSupport;

public class JobStoreAction extends ActionSupport{
  private String name;
  private String defination;
  private String cycle;
  private String userId;
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getDefination() {
    return defination;
  }
  public void setDefination(String defination) {
    this.defination = defination;
  }
  public String getCycle() {
    return cycle;
  }
  public void setCycle(String cycle) {
    this.cycle = cycle;
  }
  public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }
  
  public String execute() throws Exception {
    DBOperator operator = new DBOperator();
    operator.addNewJob(name, defination, cycle, userId);
    Timer.getInstance().schedule(name, cycle);
    return SUCCESS;
  }
}
