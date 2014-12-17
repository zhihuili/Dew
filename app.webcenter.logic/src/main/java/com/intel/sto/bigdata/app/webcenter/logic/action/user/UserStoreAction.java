package com.intel.sto.bigdata.app.webcenter.logic.action.user;

import com.intel.sto.bigdata.app.webcenter.logic.db.DBOperator;
import com.opensymphony.xwork2.ActionSupport;

public class UserStoreAction extends ActionSupport {
  public String name;
  public String password;
  public String type;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String execute() throws Exception {
    DBOperator operator = new DBOperator();
    operator.addNewUser(name, password, type);
    return SUCCESS;
  }
}
