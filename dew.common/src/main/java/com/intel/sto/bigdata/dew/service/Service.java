package com.intel.sto.bigdata.dew.service;

import com.intel.sto.bigdata.dew.message.ServiceMessage;

public abstract class Service implements Runnable {

  abstract public void stop();

  public ServiceMessage get(Object message) {
    return null;
  }

  public void put(ServiceMessage message) {

  }

}
