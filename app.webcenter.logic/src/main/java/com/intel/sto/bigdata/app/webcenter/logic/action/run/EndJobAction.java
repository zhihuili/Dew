package com.intel.sto.bigdata.app.webcenter.logic.action.run;

import com.intel.sto.bigdata.app.webcenter.logic.db.DBOperator;
import com.intel.sto.bigdata.app.sparklogparser.model.App;
import com.intel.sto.bigdata.app.webcenter.logic.util.Utils;
import com.opensymphony.xwork2.ActionSupport;

public class EndJobAction extends ActionSupport {
  private static final long serialVersionUID = 1L;
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
    App currentApp = Utils.parseSparkDriverLog(id);
    operator.changeAppStatus(id, status, currentApp.getAppId());
    return SUCCESS;
  }
}