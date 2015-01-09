package com.intel.sto.bigdata.app.webcenter.logic.action.appinfo;

import java.util.List;

import com.intel.sto.bigdata.app.logmanager.LogQuery;
import com.opensymphony.xwork2.ActionSupport;

public class LogQueryAction extends ActionSupport {

  private static final long serialVersionUID = 6332278455726983991L;
  private String appRecordId;
  private String queryWords;
  private String logResult;

  public String getAppRecordId() {
    return appRecordId;
  }

  public void setAppRecordId(String appRecordId) {
    this.appRecordId = appRecordId;
  }

  public String getQueryWords() {
    return queryWords;
  }

  public void setQueryWords(String queryWords) {
    this.queryWords = queryWords;
  }

  public String getLogResult() {
    return logResult;
  }

  public void setLogResult(String logResult) {
    this.logResult = logResult;
  }

  public String query() throws Exception {
    if (queryWords == null || queryWords.equals("")) {
      return SUCCESS;
    }
    StringBuffer sb = new StringBuffer();
    List<String> logList = LogQuery.querySingle(appRecordId, queryWords);
    for (String line : logList) {
      sb.append(line +System.getProperty("line.separator"));
    }
    logResult = sb.toString();
    return SUCCESS;
  }
}
