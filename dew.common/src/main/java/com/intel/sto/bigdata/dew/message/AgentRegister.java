package com.intel.sto.bigdata.dew.message;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AgentRegister implements Serializable {

  private static final long serialVersionUID = 1L;

  private String ip;
  private String hostName;
  private int port;
  private String url = "";
  private Set<String> services = new HashSet<String>();

  public AgentRegister() {
  }

  public AgentRegister(String ip, String hostName, int port) {
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

  public Set<String> getServices() {
    return services;
  }

  public void setServices(Set<String> services) {
    this.services = services;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof AgentRegister) {
      AgentRegister ar = (AgentRegister) o;
      return url.equals(ar.getUrl());
    }
    return false;
  }

  @Override
  public int hashCode() {
    return url.hashCode();
  }

  @Override
  public String toString() {
    return url;
  }
}
