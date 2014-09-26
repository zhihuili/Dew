package com.intel.sto.bigdata.dew.agent;

import static java.util.concurrent.TimeUnit.SECONDS;
import scala.concurrent.duration.Duration;
import akka.actor.ActorIdentity;
import akka.actor.ActorRef;
import akka.actor.Identify;
import akka.actor.ReceiveTimeout;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;

import com.intel.sto.bigdata.dew.message.AgentRegister;
import com.intel.sto.bigdata.dew.message.ServiceRequest;
import com.intel.sto.bigdata.dew.message.StartService;
import com.intel.sto.bigdata.dew.service.Service;

public class Agent extends UntypedActor {
  private String masterUrl;
  private ActorRef master;
  private ServiceManager serviceManager;
  private LoggingAdapter log = Logging.getLogger(this);

  public Agent(String masterUrl, ServiceManager serviceManager) {
    this.serviceManager = serviceManager;
    this.masterUrl = masterUrl;
    sendIdentifyRequest();
  }

  private void sendIdentifyRequest() {
    log.info("Connect master:" + masterUrl);
    getContext().actorSelection(masterUrl).tell(new Identify(masterUrl), getSelf());
    getContext()
        .system()
        .scheduler()
        .scheduleOnce(Duration.create(3, SECONDS), getSelf(), ReceiveTimeout.getInstance(),
            getContext().dispatcher(), getSelf());
  }

  @Override
  public void onReceive(Object message) throws Exception {
    if (message instanceof ActorIdentity) {
      master = ((ActorIdentity) message).getRef();
      if (master == null) {
        log.error("Master not available: " + masterUrl);
      } else {
        getContext().watch(master);
        getContext().become(active, true);
        master.tell(new AgentRegister(), getSelf());
      }
    } else if (message instanceof ReceiveTimeout) {
      sendIdentifyRequest();
    } else {
      log.warning("Master not ready yet");
    }
  }

  Procedure<Object> active = new Procedure<Object>() {
    @Override
    public void apply(Object message) {
      if (message instanceof ServiceRequest) {
        ServiceRequest serviceRequest = (ServiceRequest) message;
        Service service = serviceManager.getService(serviceRequest.getServiceName());
        if (serviceRequest.getServiceMethod().equals("get")) {
          getSender().tell(service.get(message), getSelf());
        }
      } else if (message instanceof StartService) {
        ClassLoader cl = this.getClass().getClassLoader();
        StartService ss = (StartService) message;
        try {
          Service service = (Service) cl.loadClass(ss.getServiceUri()).newInstance();
          serviceManager.putService(ss.getServiceName(), service);
          new Thread(service).start();
          getSender().tell(ss, null);
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        log.warning("Unhandle message:" + message);
        unhandled(message);
      }
    }
  };
}
