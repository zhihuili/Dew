package com.intel.sto.bigdata.dew.service.sysmetrics;

import com.intel.sto.bigdata.dew.message.ErrorMessage;
import com.intel.sto.bigdata.dew.message.ServiceResponse;
import com.intel.sto.bigdata.dew.service.Service;
import com.intel.sto.bigdata.dew.service.sysmetrics.message.DstatServiceRequest;

public class DstatService extends Service {

  private boolean run = true;
  DstatProcessor dp;

  @Override
  public void run() {
    dp = new DstatProcessor();
    dp.startThread();
    while (run) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    dp.kill();
    dp = null;
  }

  @Override
  public ServiceResponse get(Object message) {
    ServiceResponse result = new ServiceResponse();
    if (!(message instanceof DstatServiceRequest)) {
      result.setEm(new ErrorMessage("Wrong request message type, except DstatServiceRequest."));
      return result;
    }
    DstatServiceRequest request = (DstatServiceRequest) message;
    DstatProcessor sdp = dp;

    if (dp == null) {
      result.setEm(new ErrorMessage("Service is Stoped."));
      return result;
    }
    String content;
    try {
      content = sdp.findWorkloadMetrics(request.getStartTime(), request.getEndTime());
    } catch (Exception e) {
      result.setEm(new ErrorMessage(e.getMessage()));
      return result;
    }
    result.setContent(content);
    return result;
  }

  @Override
  public void stop() {
    run = false;
  }

}
