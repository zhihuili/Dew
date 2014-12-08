package com.intel.sto.bigdata.dew.service.logcollection.collector;

import com.intel.sto.bigdata.dew.message.ServiceResponse;
import com.intel.sto.bigdata.dew.service.Service;
import com.intel.sto.bigdata.dew.service.logcollection.message.LogCollectionRequest;

public class LogCollectorService extends Service {

  @Override
  public ServiceResponse get(Object message) {
    LogCollectorProcessor processor = new LogCollectorProcessor((LogCollectionRequest) message);
    processor.start();
    return new ServiceResponse();
  }

  @Override
  public void run() {
  }

  @Override
  public void stop() {
  }

}
