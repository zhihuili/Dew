package com.intel.sto.bigdata.dew.message;

import java.io.Serializable;

public class AgentInfo implements Serializable {

  private static final long serialVersionUID = 1L;

  private String ip;
  private String hostName;
  private int port;
  private String url;

  public AgentInfo() {
  }

  public AgentInfo(String ip, String hostName, int port) {
    this.ip = ip;
    this.hostName = hostName;
    this.port = port;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getHostName() {
    return hostName;
  }

  public void setHostName(String hostName) {
    this.hostName = hostName;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

}
