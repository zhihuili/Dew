package com.intel.sto.bigdata.dew.service.logcollection.message;

public class HttpDstatServiceRequest extends DstatServiceRequest {

  private String httpUrl;
  private static final long serialVersionUID = 7537572480002624685L;

  public HttpDstatServiceRequest(String httpUrl, long startTime, long endTime) {
    super(startTime, endTime);
    this.httpUrl = httpUrl;
  }

  public String getHttpUrl() {
    return httpUrl;
  }

  public void setHttpUrl(String httpUrl) {
    this.httpUrl = httpUrl;
  }

}
