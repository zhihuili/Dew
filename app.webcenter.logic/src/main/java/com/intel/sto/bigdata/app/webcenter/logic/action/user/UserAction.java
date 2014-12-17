package com.intel.sto.bigdata.app.webcenter.logic.action.user;

import java.util.ArrayList;
import java.util.List;

import com.intel.sto.bigdata.app.webcenter.logic.action.bean.userBean;
import com.intel.sto.bigdata.app.webcenter.logic.db.DBOperator;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport {
  public String id;
  public String[] ids;
  public userBean user;
  public ArrayList<userBean> users;
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

  public userBean getUser() {
    return user;
  }

  public void setUser(userBean user) {
    this.user = user;
  }

  public ArrayList<userBean> getUsers() {
    return users;
  }

  public void setUsers(ArrayList<userBean> users) {
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
