package com.intel.sto.bigdata.app.webcenter.logic.action.run;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.intel.sto.bigdata.app.webcenter.logic.WebCenterContext;
import com.intel.sto.bigdata.app.webcenter.logic.db.DBOperator;
import com.intel.sto.bigdata.app.sparklogparser.model.App;
import com.intel.sto.bigdata.app.webcenter.logic.util.Utils;
import com.intel.sto.bigdata.dew.conf.DewConf;
import com.intel.sto.bigdata.dew.utils.Hdfs;
import com.opensymphony.xwork2.ActionSupport;

public class EndJobAction extends ActionSupport {
  private static final long serialVersionUID = 1L;
  private String id;
  private String status;
  private DBOperator operator = new DBOperator();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String execute() throws Exception {
    App currentApp = Utils.parseSparkDriverLog(id);
    String appId = currentApp.getAppId();
    operator.changeAppStatus(id, status, appId);
    try {
      Utils.collectSparkLog(appId);
    } catch (Exception e) {
      System.out.println("Collect distributed log failed.");
      e.printStackTrace();
    }
    try {
      Utils.runSparkPowerMeter(currentApp);
    } catch (Exception e) {
      System.out.println("Run SparkPowerMeter failed.");
      e.printStackTrace();
    }
    try {
      processDriverLog(appId);
    } catch (Exception e) {
      System.out.println("Collect Spark driver log failed.");
      e.printStackTrace();
    }
    return SUCCESS;
  }

  private void processDriverLog(String appId) throws IOException {

    File driverLogFile =
        new File(WebCenterContext.getConf().get("dew.webcenter.applogfile.path"), id);
    BufferedReader br =
        new BufferedReader(new InputStreamReader(new FileInputStream(driverLogFile)));
    String backupPath =
        Utils.getWorkloadPath() + File.separator + appId + File.separator + "driver.log";
    StringBuffer sb = new StringBuffer();

    Path logPath = new Path(File.separator + "dewlog" + File.separator + appId);
    DewConf dewConf = DewConf.getDewConf();
    FileSystem fs = Hdfs.getFileSystem(dewConf);
    if (!fs.exists(logPath)) {
      fs.mkdirs(logPath);
    }
    Path logFilePath = new Path(logPath, "driver.log");

    String line;
    while ((line = br.readLine()) != null) {
      sb.append(line + System.getProperty("line.separator"));
    }
    br.close();
    BufferedWriter bw = new BufferedWriter(new FileWriter(backupPath));
    bw.write(sb.toString());
    bw.close();
    FSDataOutputStream os = fs.create(logFilePath);
    os.write(sb.toString().getBytes());
    os.close();
  }
}
