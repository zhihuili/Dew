package com.intel.sto.bigdata.dew.service.logcollection.message;

import com.intel.sto.bigdata.dew.message.ServiceRequest;

public class LogQueryRequest extends ServiceRequest {

  private static final long serialVersionUID = 7513798159265663482L;

  LogQueryRequest() {
    super("logquery", "get");
  }

  private String fileName;
  private String httpUrl;
  private String appId;
  private String words;

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getHttpUrl() {
    return httpUrl;
  }

  public void setHttpUrl(String httpUrl) {
    this.httpUrl = httpUrl;
  }

  public String getAppId() {
    return appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public String getWords() {
    return words;
  }

  public void setWords(String words) {
    this.words = words;
  }

}
