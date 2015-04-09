package com.intel.sto.bigdata.dew.service.sysmetrics.web;

import com.intel.sto.bigdata.dew.utils.Host;
import com.opensymphony.xwork2.ActionSupport;

public class ViewAction extends ActionSupport {

  private static final long serialVersionUID = -6170242490646056477L;
  private String hostName;
  private String ip;

  public String execute() throws Exception {
    hostName = Host.getName();
    ip = Host.getIp();
    return SUCCESS;
  }

  public String getHostName() {
    return hostName;
  }

  public void setHostName(String hostName) {
    this.hostName = hostName;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

}
