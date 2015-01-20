package com.intel.sto.bigdata.dew.app.driver;

import static akka.pattern.Patterns.ask;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import akka.actor.ActorIdentity;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Identify;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;

import com.intel.sto.bigdata.dew.app.AppDes;
import com.intel.sto.bigdata.dew.app.AppProcessor;
import com.intel.sto.bigdata.dew.conf.DewConf;
import com.intel.sto.bigdata.dew.message.AgentInfo;
import com.intel.sto.bigdata.dew.message.AgentRegister;
import com.intel.sto.bigdata.dew.message.ServiceRequest;
import com.intel.sto.bigdata.dew.message.ServiceTimeout;
import com.intel.sto.bigdata.dew.utils.Host;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;

public class MyAppDriver {
  private ActorRef master;
  private ActorSystem system;
  private Logger log = Logger.getLogger(MyAppDriver.class);
  private FiniteDuration duration = Duration.create(3, SECONDS);
  private static MyAppDriver instance;
  private Timeout timeout = new Timeout(Duration.create(30, "seconds"));

  private MyAppDriver() {
    system =
        ActorSystem.create(
            "App",
            ConfigFactory
                .load("common")
                .withValue("akka.remote.netty.tcp.port", ConfigValueFactory.fromAnyRef(0))
                .withValue("akka.remote.netty.tcp.hostname",
                    ConfigValueFactory.fromAnyRef(Host.getName())));
    String masterUrl = DewConf.getDewConf().get("master");
    String masterPath = "akka.tcp://Master@" + masterUrl + "/user/master";
    log.warn("Build MyAppDriver with master url: "+masterPath);
    
    try {
      master =
          ((ActorIdentity) ask(system.actorSelection(masterPath), new Identify(masterPath), 3000)
              .result(duration, null)).getRef();
    } catch (Throwable e) {
      log.error("Can't connect dew master:" + masterPath);
      throw new RuntimeException(e);
    }
  }

  public static synchronized MyAppDriver getMyAppDriver() {
    if (instance == null) {
      instance = new MyAppDriver();
    }
    return instance;
  }

  public void stop() {
    system.shutdown();
  }

  public ActorRef createActor(Class<?> userActor, String actorName, Object... param) {
    ActorRef ref = system.actorOf(Props.create(userActor, param), actorName);
    return ref;
  }

  public void stopActor(ActorRef ref) {
    system.stop(ref);
  }

  public Map<AgentRegister, ActorRef> getAgents(AppDes appDes) throws Exception {
    ActorRef app =
        system.actorOf(Props.create(ServiceManager.class, master, null, appDes),
            "app" + UUID.randomUUID());

    Future<Object> future = Patterns.ask(app, new AgentInfo(), timeout);
    AgentInfo result = (AgentInfo) Await.result(future, timeout.duration());
    return result.getAgentActors();
  }

  public void requestService(AppProcessor appProcessor, AppDes appDes, ServiceRequest request)
      throws Exception {
    ActorRef app =
        system.actorOf(Props.create(ServiceManager.class, master, appProcessor, appDes), "app"
            + UUID.randomUUID());
    Future<Object> future = Patterns.ask(app, request, timeout);
    try {
      Await.result(future, timeout.duration());
    } catch (Exception e) {
      // service timeout, send ServiceCompletion message forcedly.
      future = Patterns.ask(app, new ServiceTimeout(), timeout);
      Await.result(future, timeout.duration());
    } finally {
      system.stop(app);
    }
  }

}
