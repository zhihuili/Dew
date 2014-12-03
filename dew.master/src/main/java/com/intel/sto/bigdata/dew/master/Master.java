package com.intel.sto.bigdata.dew.master;

import java.util.List;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import com.intel.sto.bigdata.dew.message.AgentQuery;
import com.intel.sto.bigdata.dew.message.AgentRegister;
import com.intel.sto.bigdata.dew.message.StartService;
import com.intel.sto.bigdata.dew.service.ServiceDes;
import com.intel.sto.bigdata.dew.utils.Files;

public class Master extends UntypedActor {
  private LoggingAdapter log = Logging.getLogger(this);
  private List<ServiceDes> defaultServices;

  public Master() throws Exception {
    defaultServices = Files.loadService("/services.properties");
  }

  @Override
  public void onReceive(Object message) throws Exception {
    if (message instanceof AgentRegister) {
      AgentRegister ai = (AgentRegister) message;
      ai.setUrl(getSender().path().toString());
      ClusterState.addAgent(ai);
      log.info("Agent registered: " + ai.getUrl());
      for (ServiceDes des : defaultServices) {
        getSender().tell(des, getSelf());
      }
    } else if (message instanceof AgentQuery) {
      AgentQuery al = (AgentQuery) message;
      al.setResponseUrls(ClusterState.findAgent(al.getRequestHosts(), al.getServiceName()));
      getSender().tell(al, getSelf());
    } else if (message instanceof StartService) {
      StartService ss = (StartService) message;
      String agentUrl = getSender().path().toString();
      ClusterState.addService(agentUrl, ss.getServiceName());
      log.info(ss.getServiceName() + " added to " + agentUrl);
    } else {
      log.info("Unhandle message:" + message);
      unhandled(message);
    }

  }
}
