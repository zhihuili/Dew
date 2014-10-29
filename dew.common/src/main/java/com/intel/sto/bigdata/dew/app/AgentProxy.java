package com.intel.sto.bigdata.dew.app;

import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;

import com.intel.sto.bigdata.dew.message.ServiceRequest;
import com.intel.sto.bigdata.dew.message.ServiceTimeout;
import com.intel.sto.bigdata.dew.utils.Host;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;

public class AgentProxy {

  private ActorRef app;
  private ActorSystem system;
  private int serviceTimeout = 30;
  private int processTimeout = 30;

  public AgentProxy(String masterUrl, AppProcessor appProcessor, AppDes appDes) {
    String url = "akka.tcp://Master@" + masterUrl + "/user/master";
    system =
        ActorSystem.create(
            "App",
            ConfigFactory
                .load("common")
                .withValue("akka.remote.netty.tcp.port", ConfigValueFactory.fromAnyRef(0))
                .withValue("akka.remote.netty.tcp.hostname",
                    ConfigValueFactory.fromAnyRef(Host.getName())));
    app = system.actorOf(Props.create(AppDriver.class, url, appProcessor, appDes), "app");

  }

  public void shutDown() {
    system.shutdown();
  }

  public void requestService(ServiceRequest request) throws Exception {
    Timeout timeout = new Timeout(Duration.create(serviceTimeout, "seconds"));
    Future<Object> future = Patterns.ask(app, request, timeout);
    try {
      Await.result(future, timeout.duration());
    } catch (Exception e) {
      // get agent service timeout, send ServiceCompletion message forcedly.
      timeout = new Timeout(Duration.create(processTimeout, "seconds"));
      future = Patterns.ask(app, new ServiceTimeout(), timeout);
      Await.result(future, timeout.duration());
    } finally {
      shutDown();
    }
  }

}
