package com.intel.sto.bigdata.app.webcenter.logic.db;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import com.intel.sto.bigdata.app.sparkperformance.Util;
import com.intel.sto.bigdata.app.webcenter.logic.Constants;

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

  public void getConnection() throws IOException {
    File directory = new File("..");
    String confFilePath = directory.getCanonicalPath() + "/app.sparkpowermeter/conf.properties";
    Properties p = Util.buildProperties(confFilePath);
    db_driver = p.getProperty(Constants.DB_DRIVER);
    db_url = p.getProperty(Constants.DB_URL);
    db_username = p.getProperty(Constants.DB_USERNAME);
    db_password = p.getProperty(Constants.DB_PASSWORD);
    try {
      Class.forName(db_driver).newInstance();
      conn = DriverManager.getConnection(db_url,db_username,db_password);
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
}
