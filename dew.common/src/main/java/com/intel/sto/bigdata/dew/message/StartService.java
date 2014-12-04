package com.intel.sto.bigdata.dew.message;

import java.io.Serializable;

public class StartService implements Serializable {

  private static final long serialVersionUID = 1L;

  private String serviceName;
  private String serviceDes;

  public StartService(String name, String des) {
    this.serviceName = name;
    this.serviceDes = des;
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public String getServiceDes() {
    return serviceDes;
  }

  public void setServiceDes(String serviceDes) {
    this.serviceDes = serviceDes;
  }

}
