package com.intel.sto.bigdata.dew.service.logcollection.aggregator;

import com.intel.sto.bigdata.dew.http.server.JettyStreamServer;
import com.intel.sto.bigdata.dew.message.ServiceResponse;
import com.intel.sto.bigdata.dew.service.Service;
import com.intel.sto.bigdata.dew.utils.Host;

public class LogAggregatorService extends Service {

  private JettyStreamServer server;

  @Override
  public void run() {
    try {
      server = new JettyStreamServer(new IndividualHdfsLogHttpCallback(dewConf));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public ServiceResponse get(Object message) {
    ServiceResponse response = new ServiceResponse();
    response.setContent("http://" + Host.getName() + ":" + server.getPort());
    return response;
  }

  @Override
  public void stop() {
    server.shutDown();
  }

}
