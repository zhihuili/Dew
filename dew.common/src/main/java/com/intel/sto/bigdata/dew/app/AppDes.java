package com.intel.sto.bigdata.dew.app;

import java.util.Set;

public class AppDes {

  // agents' host name
  private Set<String> hosts;
  private String serviceName;

  public AppDes() {
  }

  public AppDes(Set<String> hosts) {
    this.hosts = hosts;
  }

  public AppDes(Set<String> hosts, String serviceName) {
    this.hosts = hosts;
    this.serviceName = serviceName;
  }

  public Set<String> getHosts() {
    return hosts;
  }

  public void setHosts(Set<String> hosts) {
    this.hosts = hosts;
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

}
