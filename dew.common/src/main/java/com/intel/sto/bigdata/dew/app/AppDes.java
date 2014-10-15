package com.intel.sto.bigdata.dew.app;

import java.util.Set;

public class AppDes {

  private Set<String> hosts;

  public AppDes() {
  }

  public AppDes(Set<String> hosts) {
    this.hosts = hosts;

  }

  public Set<String> getHosts() {
    return hosts;
  }

  public void setHosts(Set<String> hosts) {
    this.hosts = hosts;
  }

}
