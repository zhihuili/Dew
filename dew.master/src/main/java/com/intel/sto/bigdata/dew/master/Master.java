package com.intel.sto.bigdata.dew.master;

import java.util.List;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import com.intel.sto.bigdata.dew.conf.DewConf;
import com.intel.sto.bigdata.dew.conf.DewConfMessage;
import com.intel.sto.bigdata.dew.master.servicemanagement.ProcessServiceManager;
import com.intel.sto.bigdata.dew.message.AgentQuery;
import com.intel.sto.bigdata.dew.message.AgentRegister;
import com.intel.sto.bigdata.dew.message.StartService;
import com.intel.sto.bigdata.dew.service.ServiceDes;
import com.intel.sto.bigdata.dew.utils.Constants;
import com.intel.sto.bigdata.dew.utils.Files;

public class Master extends UntypedActor {
  private LoggingAdapter log = Logging.getLogger(this);
  private List<ServiceDes> defaultServices;
  private ProcessServiceManager processServiceManager;
  private DewConf dewConf;

  public Master() throws Exception {
    dewConf = DewConf.getDewConf();
    defaultServices = Files.loadService("/services.properties");
    processServiceManager = new ProcessServiceManager(defaultServices, getSelf());
    processServiceManager.start();
  }

  @Override
  public void onReceive(Object message) throws Exception {
    if (message instanceof AgentRegister) {
      AgentRegister ai = (AgentRegister) message;
      ai.setUrl(getSender().path().toString());
      ai.setAgent(getSender());
      ClusterState.addAgent(ai);
      log.info("Agent registered: " + ai.getUrl());
      for (ServiceDes des : defaultServices) {
        // only start thread service.
        if (des.getServiceType().equals(Constants.THREAD_SERVICE_TYPE)) {
          getSender().tell(des, getSelf());
        }
      }
      if (ai.getType() == Constants.BRANCH_AGENT_TYPE) {
        processServiceManager.scan();
      }
    } else if (message instanceof AgentQuery) {
      AgentQuery al = (AgentQuery) message;
      al.setResponseUrls(ClusterState.findAgent(al.getRequestHosts(), al.getServiceName()));
      getSender().tell(al, getSelf());
    } else if (message instanceof StartService) {
      StartService ss = (StartService) message;
      String agentUrl = getSender().path().toString();
      String serviceName = ss.getServiceName();
      if (serviceName != null) {
        ClusterState.addService(agentUrl, serviceName);
        log.info(ss.getServiceName() + " added to " + agentUrl);
      }
    } else if (message instanceof DewConfMessage) {
      DewConfMessage conf = (DewConfMessage) message;
      conf.setP(dewConf.getP());
      getSender().tell(conf, getSelf());
    } else {
      log.info("Unhandle message:" + message);
      unhandled(message);
    }

  }
}
