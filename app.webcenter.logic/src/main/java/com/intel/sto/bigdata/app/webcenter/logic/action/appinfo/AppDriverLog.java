package com.intel.sto.bigdata.app.webcenter.logic.action.appinfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.intel.sto.bigdata.app.webcenter.logic.util.Utils;
import com.opensymphony.xwork2.ActionSupport;

public class AppDriverLog extends ActionSupport {
  private static final long serialVersionUID = -6053798337594038264L;
  private String enginAppID;
  private String driverLogContent;

  public String getEnginAppID() {
    return enginAppID;
  }

  public void setEnginAppID(String enginAppID) {
    this.enginAppID = enginAppID;
  }

  public String getDriverLogContent() {
    return driverLogContent;
  }

  public void setDriverLogContent(String driverLogContent) {
    this.driverLogContent = driverLogContent;
  }

  public String execute() throws Exception {
    String driverLogPath =
        Utils.getWorkloadPath() + File.separator + enginAppID + File.separator + "driver.log";
    File driverLogFile = new File(driverLogPath);
    if (driverLogFile.exists()) {
      BufferedReader br =
          new BufferedReader(new InputStreamReader(new FileInputStream(driverLogFile)));
      StringBuffer sb = new StringBuffer("");
      String line;
      while ((line = br.readLine()) != null) {
        sb.append(line + "\n");
      }
      br.close();
      driverLogContent = sb.toString();
      return SUCCESS;
    } else {
      return ERROR;
    }
  }
}
