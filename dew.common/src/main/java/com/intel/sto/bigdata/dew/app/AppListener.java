package com.intel.sto.bigdata.dew.app;

import java.util.ArrayList;
import java.util.List;

import akka.actor.UntypedActor;

import com.intel.sto.bigdata.dew.message.ProcessCompletion;
import com.intel.sto.bigdata.dew.message.ServiceCompletion;
import com.intel.sto.bigdata.dew.message.ServiceResponse;

public class AppListener extends UntypedActor {

  protected List<ServiceResponse> responseList = new ArrayList<ServiceResponse>();
  protected AppDes appDes;
  AppProcessor processor;

  public AppListener(AppProcessor processor) {
    this.processor = processor;
  }

  @Override
  public void onReceive(Object message) throws Exception {
    if (message instanceof ServiceCompletion) {
      processor.process(responseList);
      getSender().tell(new ProcessCompletion(), getSelf());
    }
    if (message instanceof ServiceResponse) {
      responseList.add((ServiceResponse) message);
    }
  }

}
