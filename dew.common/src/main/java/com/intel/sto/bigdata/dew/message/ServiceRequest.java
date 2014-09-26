package com.intel.sto.bigdata.dew.message;

import java.io.Serializable;

public class ServiceRequest implements Serializable {

  private static final long serialVersionUID = 1627299820739118428L;
  private String serviceName;
  private String serviceMethod;

  public ServiceRequest() {
  }

  public ServiceRequest(String serviceName, String serviceMethod) {
    this.serviceMethod = serviceMethod;
    this.serviceName = serviceName;
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public String getServiceMethod() {
    return serviceMethod;
  }

  public void setServiceMethod(String serviceMethod) {
    this.serviceMethod = serviceMethod;
  }

}
