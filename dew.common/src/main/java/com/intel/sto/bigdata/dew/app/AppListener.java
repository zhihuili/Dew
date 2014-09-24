package com.intel.sto.bigdata.dew.app;

import java.util.ArrayList;
import java.util.List;

import akka.actor.UntypedActor;

import com.intel.sto.bigdata.dew.message.ServiceComplete;
import com.intel.sto.bigdata.dew.message.ServiceResponse;

public abstract class AppListener extends UntypedActor {

  protected List<String> responseList = new ArrayList<String>();

  @Override
  public void onReceive(Object message) throws Exception {
    if (message instanceof ServiceComplete) {
      process();
      getContext().system().shutdown();
    }
    if (message instanceof ServiceResponse) {
      responseList.add(((ServiceResponse) message).getContent());
    }
  }

  abstract public void process();
}
