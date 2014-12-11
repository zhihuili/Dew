package com.intel.sto.bigdata.dew.agent;

import java.io.IOException;

import akka.actor.ActorIdentity;
import akka.actor.ActorRef;
import akka.actor.Identify;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import com.intel.sto.bigdata.dew.message.AgentRegister;
import com.intel.sto.bigdata.dew.message.ServiceRequest;
import com.intel.sto.bigdata.dew.message.ServiceResponse;
import com.intel.sto.bigdata.dew.message.StartService;
import com.intel.sto.bigdata.dew.service.Service;
import com.intel.sto.bigdata.dew.service.ServiceDes;
import com.intel.sto.bigdata.dew.utils.Constants;
import com.intel.sto.bigdata.dew.utils.Host;
import com.intel.sto.bigdata.dew.utils.Util;

public class Agent extends UntypedActor {
  private String masterUrl;
  private ActorRef master;
  private ServiceManager serviceManager;
  private ServiceDes defaultServiceDes;
  private LoggingAdapter log = Logging.getLogger(this);

  public Agent(String masterUrl, ServiceManager serviceManager, String serviceDes) {
    this.serviceManager = serviceManager;
    this.masterUrl = masterUrl;
    if (serviceDes != null) {
      defaultServiceDes = new ServiceDes();
      defaultServiceDes.deSerialize(serviceDes);// TODO exit if failed.
      processStartService(defaultServiceDes);
    }
    sendIdentifyRequest();
  }

  private void sendIdentifyRequest() {
    log.info("Connect master:" + masterUrl);
    getContext().actorSelection(masterUrl).tell(new Identify(masterUrl), getSelf());
    // getContext()
    // .system()
    // .scheduler()
    // .scheduleOnce(Duration.create(5, SECONDS), getSelf(), ReceiveTimeout.getInstance(),
    // getContext().dispatcher(), getSelf());
  }

  @Override
  public void onReceive(Object message) throws Exception {
    if (message instanceof ActorIdentity) {
      master = ((ActorIdentity) message).getRef();
      if (master == null) {
        log.error("Master not available: " + masterUrl);
      } else {
        // getContext().watch(master);
        // getContext().become(active, true);
        AgentRegister ar = new AgentRegister(Host.getIp(), Host.getName(), 0);
        if (defaultServiceDes == null) {
          ar.setType(Constants.BRANCH_AGENT_TYPE);
        } else {
          ar.setType(Constants.LEAF_AGENT_TYPE);
          ar.getServices().add(defaultServiceDes.getServiceName());
        }
        master.tell(ar, getSelf());
      }
    } else if (message instanceof ServiceRequest) {
      ServiceRequest serviceRequest = (ServiceRequest) message;
      Service service = serviceManager.getService(serviceRequest.getServiceName());
      if (service != null && serviceRequest.getServiceMethod().equals("get")) {
        ServiceResponse sr = service.get(message);
        sr.setNodeName(Host.getName());
        sr.setIp(Host.getIp());
        getSender().tell(sr, getSelf());
      }
    } else if (message instanceof ServiceDes) {
      // TODO ugly code, I will refactor it by create BranchAgent and LeafAgent.
      if (defaultServiceDes == null) {
        String serviceName = processStartService(message);
        if (serviceName != null) {
          getSender().tell(new StartService(serviceName, null), getSelf());
        }
      } else {// the agent only start one service (defalutServiceDes)
        getSender().tell(new StartService(defaultServiceDes.getServiceName(), null), getSelf());
      }
    } else {
      log.warning("Unhandled message:" + message);
      unhandled(message);
    }
  }

  private String processStartService(Object message) {
    ServiceDes sd = (ServiceDes) message;

    if (sd.getServiceType().toLowerCase().equals(Constants.THREAD_SERVICE_TYPE)) {
      ClassLoader cl = this.getClass().getClassLoader();
      try {
        Service service = (Service) cl.loadClass(sd.getServiceClass()).newInstance();
        serviceManager.putService(sd.getServiceName(), service);
        new Thread(service).start();
      } catch (Exception e) {
        e.printStackTrace();
      }
      return sd.getServiceName();
    }
    if (sd.getServiceType().toLowerCase().equals(Constants.PROCESS_SERVICE_TYPE)) {
      sd.setServiceType(Constants.THREAD_SERVICE_TYPE);
      String cp = Util.findDewClassPath();
      if (cp == null) {
        log.error("start process service failed, classpath is null.");
        return null;
      }

      String des = sd.serialize();
      Runtime runtime = Runtime.getRuntime();
      try {
        Process process =
            runtime.exec("java -cp " + cp + " com.intel.sto.bigdata.dew.agent.DewDrop " + masterUrl
                + " " + des);
        serviceManager.putProcess(sd.getServiceName(), process);
        ProcessPrinter pp = new ProcessPrinter(getSelf().path().toString(),process);
        pp.start();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return null;// not register process service

  }

}
