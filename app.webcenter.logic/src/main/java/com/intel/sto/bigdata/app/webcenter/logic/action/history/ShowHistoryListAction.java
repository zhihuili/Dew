package com.intel.sto.bigdata.app.webcenter.logic.action.history;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.apache.struts2.ServletActionContext;

import com.intel.sto.bigdata.app.sparkperformance.Constants;
import com.intel.sto.bigdata.app.sparkperformance.Util;
import com.opensymphony.xwork2.ActionSupport;

public class ShowHistoryListAction extends ActionSupport {

  private static final long serialVersionUID = -466813403649985766L;
  public HashMap<String, String> historyDirList;

  public String execute() throws IOException {
    historyDirList = new HashMap<String, String>();

    String confPath =
        ServletActionContext.getServletContext().getRealPath(File.separator)
            + "/../../../app.sparkpowermeter/conf.properties";
    Properties p = Util.buildProperties(confPath);
    String backupPath = p.getProperty("workload.output.path");

    File root = new File(backupPath);
    File[] files = root.listFiles();

    for (File file : files) {
      if (file.isDirectory()) {
        historyDirList.put(file.getName(), file.toString());
      }
    }

    return SUCCESS;
  }

  public HashMap<String, String> getHistoryDirList() {
    return historyDirList;
  }

  public void setHistoryDirList(HashMap<String, String> historyDirList) {
    this.historyDirList = historyDirList;
  }

}
