package com.intel.sto.bigdata.app.webcenter.logic.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import com.intel.sto.bigdata.app.webcenter.logic.Constants;
import com.intel.sto.bigdata.app.webcenter.logic.WebCenterContext;

public class DBService {
  private static String db_driver;
  private static String db_url;
  private static String db_username;
  private static String db_password;

  protected Connection conn;
  protected Statement s;

  public DBService() {
    conn = null;
    s = null;
  }

  public void getConnection() throws Exception {
    Map<String, String> JDBCConf = WebCenterContext.getConf();
    db_driver = JDBCConf.get(Constants.DB_DRIVER);
    db_url = JDBCConf.get(Constants.DB_URL);
    db_username = JDBCConf.get(Constants.DB_USERNAME);
    db_password = JDBCConf.get(Constants.DB_PASSWORD);
    try {
      Class.forName(db_driver).newInstance();
      conn = DriverManager.getConnection(db_url, db_username, db_password);
    } catch (Exception e) {
      e.toString();
      e.printStackTrace();
    }
  }

  public void closeConnection() {
    try {
      conn.close();
      conn = null;
      s.close();
      s = null;
    } catch (SQLException e) {
      e.toString();
      e.printStackTrace();
    }
  }

  public ResultSet executeSelect(String sql) {
    ResultSet rs = null;
    try {
      s = conn.createStatement();
      rs = s.executeQuery(sql);
    } catch (SQLException e) {
      e.toString();
      e.printStackTrace();
    }
    return rs;
  }

  public boolean executeNoSelect(String sql) {
    int result = 0;
    try {
      s = conn.createStatement();
      result = s.executeUpdate(sql);
    } catch (SQLException e) {
      e.toString();
      e.printStackTrace();
    }

    if (result == 1)
      return true;
    else
      return false;
  }
}