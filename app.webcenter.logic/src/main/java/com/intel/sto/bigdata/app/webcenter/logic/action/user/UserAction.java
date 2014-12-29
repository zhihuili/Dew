package com.intel.sto.bigdata.app.webcenter.logic.action.user;

import java.util.ArrayList;
import java.util.List;

import com.intel.sto.bigdata.app.webcenter.logic.action.bean.UserBean;
import com.intel.sto.bigdata.app.webcenter.logic.db.DBOperator;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport {
  public String id;
  public String[] ids;
  public UserBean user;
  public ArrayList<UserBean> users;
  public DBOperator operator = new DBOperator();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String[] getIds() {
    return ids;
  }

  public void setIds(String[] ids) {
    this.ids = ids;
  }

  public UserBean getUser() {
    return user;
  }

  public void setUser(UserBean user) {
    this.user = user;
  }

  public ArrayList<UserBean> getUsers() {
    return users;
  }

  public void setUsers(ArrayList<UserBean> users) {
    this.users = users;
  }

  public String list() throws Exception {
    users = operator.getAllUser();
    return SUCCESS;
  }

  public String load() throws Exception {
    user = operator.getSingleUser(id);
    return SUCCESS;
  }

  public String modify() throws Exception {
    operator.userModify(user);
    return SUCCESS;
  }
}