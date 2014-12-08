package com.intel.sto.bigdata.dew.service.logcollection.message;

import java.util.List;

import com.intel.sto.bigdata.dew.message.ServiceRequest;

public class LogCollectionRequest extends ServiceRequest {

  private static final long serialVersionUID = 6300497824610666154L;

  String appId;
  List<String> logPathList;
  String httpUrl;

  public LogCollectionRequest() {
    super("logcollection", "get");
  }

  public String getAppId() {
    return appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public List<String> getLogPathList() {
    return logPathList;
  }

  public void setLogPathList(List<String> logPathList) {
    this.logPathList = logPathList;
  }

  public String getHttpUrl() {
    return httpUrl;
  }

  public void setHttpUrl(String httpUrl) {
    this.httpUrl = httpUrl;
  }

}
