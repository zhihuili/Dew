package com.intel.sto.bigdata.app.webcenter.logic.db;

//import org.apache.derby.iapi.sql.ResultSet;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

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

  public ArrayList<UserBean> getAllUser() throws Exception {
    ArrayList<UserBean> result = new ArrayList<UserBean>();

    String sql = "select * from userinfo";
    getConnection();
    ResultSet rs = executeSelect(sql);

    while (rs.next()) {
      UserBean singleUser = new UserBean();
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

    getConnection();
    executeNoSelect(sql);
    closeConnection();
  }

  public UserBean getSingleUser(String id) throws Exception {
    UserBean result = new UserBean();

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

  public void userModify(UserBean user) throws Exception {
    String sql =
        "update userinfo set name='" + user.name + "', password='" + user.password + "', type='"
            + user.type + "' where user_id=" + user.id;

    getConnection();
    executeNoSelect(sql);
    closeConnection();
  }

  public ArrayList<AppBean> getAllApp() throws Exception {
    ArrayList<AppBean> result = new ArrayList<AppBean>();

    String sql = "select * from application";
    getConnection();
    ResultSet rs = executeSelect(sql);

    while (rs.next()) {
      AppBean singleApp = new AppBean();
      singleApp.setAppId(rs.getInt("app_id"));
      singleApp.setName(rs.getString("name"));
      singleApp.setPath(rs.getString("path"));
      singleApp.setExecutable(rs.getString("executable"));
      singleApp.setStrategy(rs.getString("strategy"));
      singleApp.setType(rs.getString("type"));
      singleApp.setHost(rs.getString("host"));
      result.add(singleApp);
    }
    rs.close();
    closeConnection();
    return result;
  }

  public void addNewApp(String name, String path, String executable, String strategy, String type,
      String host) throws Exception {

    String sql =
        "insert into application(name,path,executable,strategy,type,host) values('" + name + "','"
            + path + "','" + executable + "','" + strategy + "','" + type + "','" + host + "')";

    getConnection();
    executeNoSelect(sql);
    closeConnection();
  }

  public void appModify(AppBean app) throws Exception {
    String sql =
        "update application set name='" + app.name + "', path='" + app.path + "', executable='"
            + app.executable + "', strategy='" + app.strategy + "', type='" + app.type
            + "', host='" + app.host + "' where app_id=" + app.appId;

    getConnection();
    executeNoSelect(sql);
    closeConnection();
  }

  public AppBean getSingleApp(String app_id) throws Exception {
    AppBean result = new AppBean();

    String sql = "select * from application where app_id=" + app_id;
    getConnection();
    ResultSet rs = executeSelect(sql);

    while (rs.next()) {
      result.setAppId(rs.getInt("app_id"));
      result.setName(rs.getString("name"));
      result.setPath(rs.getString("path"));
      result.setExecutable(rs.getString("executable"));
      result.setStrategy(rs.getString("strategy"));
      result.setType(rs.getString("type"));
      result.setHost(rs.getString("host"));
    }
    rs.close();
    closeConnection();

    return result;
  }

  public AppBean getSingleAppByName(String name) throws Exception {
    AppBean result = new AppBean();

    String sql = "select * from application where name='" + name + "'";
    getConnection();
    ResultSet rs = executeSelect(sql);

    while (rs.next()) {
      result.setAppId(rs.getInt("app_id"));
      result.setName(rs.getString("name"));
      result.setPath(rs.getString("path"));
      result.setExecutable(rs.getString("executable"));
      result.setStrategy(rs.getString("strategy"));
      result.setType(rs.getString("type"));
      result.setHost(rs.getString("host"));
    }
    rs.close();
    closeConnection();

    return result;
  }

  public ArrayList<JobBean> getAllJob() throws Exception {
    ArrayList<JobBean> result = new ArrayList<JobBean>();

    String sql = "select * from job";
    getConnection();
    ResultSet rs = executeSelect(sql);

    while (rs.next()) {
      JobBean singleJob = new JobBean();
      singleJob.setJobId(rs.getInt("job_id"));
      singleJob.setName(rs.getString("name"));
      singleJob.setDefination(rs.getString("defination"));
      singleJob.setCycle(rs.getString("cycle"));
      singleJob.setUserId(rs.getInt("user_id"));
      result.add(singleJob);
    }
    rs.close();
    closeConnection();
    return result;
  }

  public void addNewJob(String name, String defination, String cycle, String userId)
      throws Exception {

    String sql =
        "insert into job(name,defination,cycle,user_id) values('" + name + "','" + defination
            + "','" + cycle + "'," + userId + ")";

    getConnection();
    executeNoSelect(sql);
    closeConnection();
  }

  public void jobModify(JobBean job) throws Exception {
    String sql =
        "update job set name='" + job.name + "', defination='" + job.defination + "', cycle='"
            + job.cycle + "', user_id=" + job.userId + " where job_id=" + job.jobId;

    getConnection();
    executeNoSelect(sql);
    closeConnection();
  }

  public JobBean getSingleJob(String jobId) throws Exception {
    JobBean result = new JobBean();

    String sql = "select * from job where job_id=" + jobId;
    getConnection();
    ResultSet rs = executeSelect(sql);

    while (rs.next()) {
      result.setJobId(rs.getInt("job_id"));
      result.setName(rs.getString("name"));
      result.setDefination(rs.getString("defination"));
      result.setCycle(rs.getString("cycle"));
      result.setUserId(rs.getInt("user_id"));
    }
    rs.close();
    closeConnection();

    return result;
  }

  public JobBean getSingleJobByName(String name) throws Exception {
    JobBean result = new JobBean();

    String sql = "select * from job where name='" + name + "'";
    getConnection();
    ResultSet rs = executeSelect(sql);

    while (rs.next()) {
      result.setJobId(rs.getInt("job_id"));
      result.setName(rs.getString("name"));
      result.setDefination(rs.getString("defination"));
      result.setCycle(rs.getString("cycle"));
      result.setUserId(rs.getInt("user_id"));
    }
    rs.close();
    closeConnection();

    return result;
  }

  public String addNewJobRecord(int jobId) throws Exception {
    String recordId = UUID.randomUUID().toString();
    java.util.Date date = new java.util.Date();
    Timestamp timestamp = new Timestamp(date.getTime());

    String sql =
        "insert into jobrecord(record_id,job_id,starttime,endtime,result) values('" + recordId
            + "'," + jobId + ",'" + timestamp + "','" + timestamp + "','running')";
    getConnection();
    executeNoSelect(sql);
    closeConnection();

    return recordId;
  }

  public void changeJobStatus(String recordId, String status) throws Exception {
    java.util.Date date = new java.util.Date();
    Timestamp timestamp = new Timestamp(date.getTime());
    String sql =
        "update jobrecord set endtime='" + timestamp + "', result='" + status
            + "' where record_id='" + recordId + "'";
    getConnection();
    executeNoSelect(sql);
    closeConnection();
  }
}