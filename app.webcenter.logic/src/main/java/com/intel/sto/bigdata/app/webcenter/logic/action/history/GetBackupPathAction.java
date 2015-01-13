package com.intel.sto.bigdata.app.webcenter.logic.action.history;

import java.io.File;

import com.intel.sto.bigdata.app.webcenter.logic.util.Utils;
import com.opensymphony.xwork2.ActionSupport;

public class GetBackupPathAction extends ActionSupport {
  private static final long serialVersionUID = 433297270829590702L;
  private String enginID;
  private String path;

  public String getEnginID() {
    return enginID;
  }

  public void setEnginID(String enginID) {
    this.enginID = enginID;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String execute() throws Exception {

    String backupPath = Utils.getWorkloadPath();
    File tmpPath = new File(backupPath, enginID);
    path = tmpPath.toString();
    return SUCCESS;
  }

}
