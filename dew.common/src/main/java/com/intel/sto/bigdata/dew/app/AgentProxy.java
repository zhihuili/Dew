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
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;

public class AgentProxy {

  private ActorRef app;
  private ActorSystem system;

  public AgentProxy(String masterUrl, AppProcessor appProcessor, AppDes appDes) {
    String url = "akka.tcp://Master@" + masterUrl + "/user/master";
    system =
        ActorSystem.create(
            "Agent",
            ConfigFactory.load("common").withValue("akka.remote.netty.tcp.port",
                ConfigValueFactory.fromAnyRef(0)));
    app = system.actorOf(Props.create(AppDriver.class, url, appProcessor, appDes), "app");

  }

  public void shutDown() {
    system.shutdown();
  }

  public void requestService(ServiceRequest request) {
    Timeout timeout = new Timeout(Duration.create(60, "seconds"));
    Future<Object> future = Patterns.ask(app, request, timeout);
    try {
      Await.result(future, timeout.duration());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      shutDown();
    }
  }

  public static void main(String[] args) {
    new AgentProxy("akka.tcp://Master@127.0.0.1:2052/user/master", null, null);
  }
}
