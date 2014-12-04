package com.intel.sto.bigdata.app.webcenter.logic.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

//import org.apache.derby.iapi.sql.ResultSet;

public class DBService {
  private final static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
  private final static String protocol = "jdbc:derby:";
  String dbName;

  protected Connection conn;
  protected Statement s;

  public DBService() {
    conn = null;
    s = null;
    File directory = new File("..");
    try {
      dbName = directory.getCanonicalPath() + "/Database/dewdb";
      System.out.println(" " + dbName);
    } catch (Exception e) {
    }
  }

  public void getConnection() {
    try {
      Class.forName(driver).newInstance();
      conn = DriverManager.getConnection(protocol + dbName);
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
