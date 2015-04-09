package com.intel.sto.bigdata.app.webcenter.logic.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.intel.sto.bigdata.app.webcenter.logic.Constants;
import com.intel.sto.bigdata.app.webcenter.logic.WebCenterContext;
import com.intel.sto.bigdata.app.webcenter.logic.action.bean.JobBean;
import com.intel.sto.bigdata.app.webcenter.logic.db.DBOperator;
import com.intel.sto.bigdata.app.webcenter.logic.timer.Timer;
import com.intel.sto.bigdata.dew.app.driver.MyAppDriver;
import com.intel.sto.bigdata.dew.http.server.JettyStreamServer;
import com.intel.sto.bigdata.dew.service.sysmetrics.app.CircleLink;
import com.intel.sto.bigdata.dew.service.sysmetrics.app.GroupMetricsApp;
import com.intel.sto.bigdata.dew.utils.Files;

public class InitServlet extends HttpServlet {

  private static final long serialVersionUID = 4332043419658111991L;
  private JettyStreamServer server;
  private GroupMetricsApp dstatApp;
  private static final int DSTAT_INTERVAL = 2000;
  private Logger log = Logger.getLogger(InitServlet.class);

  @Override
  public void init(ServletConfig config) throws ServletException {

    try {
      // init file stream http server
      server = new JettyStreamServer(new FileSaveCallback());
      WebCenterContext.put(Constants.FILE_SERVER_PORT, server.getPort());

      // init WebCenterContext
      String dewHome = System.getenv("DEW_HOME");
      Map<String, String> conf = Files.loadPropertiesFile("/conf.properties.default");
      File confFile = new File(dewHome, "app.webcenter/conf.properties");
      if (confFile.exists()) {
        System.out.println("Load conf from " + confFile.getAbsolutePath());
        Map<String, String> userConf = Files.loadPropertiesFile(new FileInputStream(confFile));
        conf.putAll(userConf);
      }
      WebCenterContext.putConf(conf);

      // init cluster system performance data for home page
      CircleLink cl = CircleLink.getInstance();
      dstatApp = GroupMetricsApp.getInstance();
      dstatApp.startCollectCulsterDstat(cl, DSTAT_INTERVAL);

      // init quartz scheduler
      Timer timer = Timer.getInstance();
      DBOperator operator = new DBOperator();
      ArrayList<JobBean> jobs = operator.getAllJob();
      for (JobBean job : jobs) {
        try {
          timer.schedule(job.getName(), job.getCycle());
        } catch (Throwable e) {
          log.error(e.getMessage());
        }
      }
    } catch (Exception e) {
      log.error(e.getMessage());

    }
    try {
      dbExist();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public void destroy() {
    server.shutDown();
    MyAppDriver.getMyAppDriver().stop();
  }

  private void dbExist() throws SQLException {
    Map<String, String> jdbcConf = WebCenterContext.getConf();
    String dbDriver = jdbcConf.get(Constants.DB_DRIVER);
    String dbURL = jdbcConf.get(Constants.DB_URL);
    String dbUsername = jdbcConf.get(Constants.DB_USERNAME);
    String dbPassword = jdbcConf.get(Constants.DB_PASSWORD);
    try {
      Class.forName(dbDriver).newInstance();
      Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
      conn.close();
    } catch (Exception e) {
      Connection conn =
          DriverManager.getConnection(dbURL + ";create=true;user=" + dbUsername + ";password="
              + dbPassword);
      Statement st = conn.createStatement();
      st.execute("create table userinfo(user_id int generated always as identity,name "
          + "varchar(10) NOT NULL,password varchar(20) NOT NULL,type varchar(20) "
          + "NOT NULL,primary key(user_id))");
      st.execute("insert into userinfo(name,password,type) values('admin','admin','Admin')");
      st.execute("create table application(app_id int generated always as identity,name "
          + "varchar(100) NOT NULL,path varchar(1000) NOT NULL,executable varchar(100) "
          + "NOT NULL,strategy varchar(15) NOT NULL,type varchar(15) NOT NULL,"
          + "host varchar(100) NOT NULL,primary key(app_id))");
      st.execute("create table job(job_id int generated always as identity,name "
          + "varchar(100) NOT NULL,defination varchar(2000),cycle varchar(20),user_id int,"
          + "primary key(job_id))");
      st.execute("create table jobrecord(record_id varchar(100) NOT NULL,job_id int "
          + "NOT NULL,starttime timestamp NOT NULL,endtime timestamp NOT NULL,"
          + "result varchar(20) NOT NULL,primary key(record_id))");
      st.execute("create table apprecord(record_id varchar(100) NOT NULL,app_name "
          + "varchar(100) NOT NULL,job_recordid varchar(100) NOT NULL,engin_app_id "
          + "varchar(100),starttime timestamp NOT NULL,endtime timestamp NOT NULL,"
          + "result varchar(20) NOT NULL,primary key(record_id))");
      st.close();
      conn.close();
    }
  }
}
