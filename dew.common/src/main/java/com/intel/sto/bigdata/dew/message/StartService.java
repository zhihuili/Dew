package com.intel.sto.bigdata.dew.message;

import java.io.Serializable;

public class StartService implements Serializable {

  private static final long serialVersionUID = 1L;

  private String serviceName;
  private String serviceUri;

  public StartService(String name, String uri) {
    this.serviceName = name;
    this.serviceUri = uri;
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public String getServiceUri() {
    return serviceUri;
  }

  public void setServiceUri(String serviceUri) {
    this.serviceUri = serviceUri;
  }

}
