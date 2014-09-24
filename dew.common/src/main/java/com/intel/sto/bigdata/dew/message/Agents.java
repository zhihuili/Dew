package com.intel.sto.bigdata.dew.message;

import java.io.Serializable;

public class Agents implements Serializable {

  private static final long serialVersionUID = 1L;

  private String requestHosts;
  private String responseUrls;

  public String getRequestHosts() {
    return requestHosts;
  }

  public void setRequestHosts(String requestHosts) {
    this.requestHosts = requestHosts;
  }

  public String getResponseUrls() {
    return responseUrls;
  }

  public void setResponseUrls(String responseUrls) {
    this.responseUrls = responseUrls;
  }

}
