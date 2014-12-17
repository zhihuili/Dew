package com.intel.sto.bigdata.app.webcenter.logic.db;

//import org.apache.derby.iapi.sql.ResultSet;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.intel.sto.bigdata.app.webcenter.logic.action.bean.*;

public class DBOperator extends DBService {
  // TODO add the specific function according to SQL script
  public boolean login(String username, String password) throws Exception {
    String sql =
        "select * from userinfo where name='" + username + "' and password='" + password + "'";
    boolean result = false;

    getConnection();
    ResultSet rs = executeSelect(sql);
    if (rs.next()) {
      result = true;
    }

    rs.close();
    closeConnection();

    return result;
  }

  public ArrayList<userBean> getAllUser() throws Exception {
    ArrayList<userBean> result = new ArrayList<userBean>();

    String sql = "select * from userinfo";
    getConnection();
    ResultSet rs = executeSelect(sql);

    while (rs.next()) {
      userBean singleUser = new userBean();
      singleUser.setId(rs.getInt("user_id"));
      singleUser.setName(rs.getString("name"));
      singleUser.setPassword(rs.getString("password"));
      singleUser.setType(rs.getString("type"));
      result.add(singleUser);
    }
    rs.close();
    closeConnection();
    return result;
  }

  public void addNewUser(String name, String password, String type) throws Exception {

    String sql =
        "insert into userinfo(name,password,type) values('" + name + "','" + password + "','"
            + type + "')";

    boolean result = false;

    getConnection();
    result = executeNoSelect(sql);
    closeConnection();
  }

  public userBean getSingleUser(String id) throws Exception {
    userBean result = new userBean();

    String sql = "select * from userinfo where user_id=" + id;
    getConnection();
    ResultSet rs = executeSelect(sql);

    while (rs.next()) {
      result.setId(rs.getInt("user_id"));
      result.setName(rs.getString("name"));
      result.setPassword(rs.getString("password"));
      result.setType(rs.getString("type"));
    }
    rs.close();
    closeConnection();

    return result;
  }

  public void userModify(userBean user) throws Exception {
    String sql =
        "update userinfo set name='" + user.name + "', password='" + user.password + "', type='"
            + user.type + "' where user_id=" + user.id;
    boolean result = false;
    getConnection();
    result = executeNoSelect(sql);
    closeConnection();
  }

  public ArrayList<appBean> getAllApp() throws Exception {
    ArrayList<appBean> result = new ArrayList<appBean>();

    String sql = "select * from application";
    getConnection();
    ResultSet rs = executeSelect(sql);

    while (rs.next()) {
      appBean singleApp = new appBean();
      singleApp.setApp_id(rs.getInt("app_id"));
      singleApp.setName(rs.getString("name"));
      singleApp.setPath(rs.getString("path"));
      singleApp.setExecutable(rs.getString("executable"));
      singleApp.setStrategy(rs.getString("strategy"));
      singleApp.setType(rs.getString("type"));
      result.add(singleApp);
    }
    rs.close();
    closeConnection();
    return result;
  }

  public void addNewApp(String name, String path, String executable, String strategy, String type)
      throws Exception {

    String sql =
        "insert into application(name,path,executable,strategy,type) values('" + name + "','"
            + path + "','" + executable + "','" + strategy + "','" + type + "')";

    boolean result = false;

    getConnection();
    result = executeNoSelect(sql);
    closeConnection();
  }

  public void appModify(appBean app) throws Exception {
    String sql =
        "update application set name='" + app.name + "', path='" + app.path + "', executable='"
            + app.executable + "', strategy='" + app.strategy + "', type='" + app.type
            + "' where app_id=" + app.app_id;
    boolean result = false;
    getConnection();
    result = executeNoSelect(sql);
    closeConnection();
  }

  public appBean getSingleApp(String app_id) throws Exception {
    appBean result = new appBean();

    String sql = "select * from application where app_id=" + app_id;
    getConnection();
    ResultSet rs = executeSelect(sql);

    while (rs.next()) {
      result.setApp_id(rs.getInt("app_id"));
      result.setName(rs.getString("name"));
      result.setPath(rs.getString("path"));
      result.setExecutable(rs.getString("executable"));
      result.setStrategy(rs.getString("strategy"));
      result.setType(rs.getString("type"));
    }
    rs.close();
    closeConnection();

    return result;
  }
}
