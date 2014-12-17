package com.intel.sto.bigdata.dew.service.shellexecutor.service;

import java.io.File;

import com.intel.sto.bigdata.dew.message.ErrorMessage;
import com.intel.sto.bigdata.dew.message.ServiceResponse;
import com.intel.sto.bigdata.dew.service.Service;
import com.intel.sto.bigdata.dew.service.shellexecutor.message.ExecuteRequest;

public class ExecuteService extends Service {

  @Override
  public void run() {

  }

  @Override
  public ServiceResponse get(Object message) {
    ExecuteRequest request = (ExecuteRequest) message;
    File commandFile = new File(request.getDirectory(), request.getCommand());
    ServiceResponse response = new ServiceResponse();
    if (!commandFile.exists()) {
      response.setEm(new ErrorMessage("Can't find command."));
    }
    ExecuteThread thread = new ExecuteThread(request);
    thread.start();
    return response;
  }

  @Override
  public void stop() {

  }

}
