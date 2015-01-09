package com.intel.sto.bigdata.app.webcenter.logic.action.run;

import com.intel.sto.bigdata.app.webcenter.logic.db.DBOperator;
import com.opensymphony.xwork2.ActionSupport;

public class EndJobAction extends ActionSupport {
  private String id;
  private String status;
  private DBOperator operator = new DBOperator();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String execute() throws Exception {
    operator.changeJobStatus(id, status);
    return SUCCESS;
  }
}