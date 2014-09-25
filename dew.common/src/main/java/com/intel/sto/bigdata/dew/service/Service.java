package com.intel.sto.bigdata.dew.service;

import com.intel.sto.bigdata.dew.message.ServiceResponse;

public abstract class Service implements Runnable {

  abstract public void stop();

  public ServiceResponse get(Object message) {
    return null;
  }

  public void put(ServiceResponse message) {

  }

}
