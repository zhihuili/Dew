package com.intel.sto.bigdata.app.webcenter.logic.action.login;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.intel.sto.bigdata.app.webcenter.logic.db.DBOperator;

public class LoginAction extends ActionSupport {
  private String username;
  private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String execute() throws Exception {
    DBOperator operator = new DBOperator();

    boolean logonResult = operator.login(username.trim(), password.trim());

    if (logonResult) {
      ActionContext.getContext().getSession().put("currentUser", getUsername());
      return SUCCESS;
    } else {
      return ERROR;
    }
  }
}
