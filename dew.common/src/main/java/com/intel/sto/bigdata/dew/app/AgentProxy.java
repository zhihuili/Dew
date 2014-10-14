package com.intel.sto.bigdata.dew.app;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import com.intel.sto.bigdata.dew.message.ServiceRequest;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;

public class AgentProxy {

  ActorRef app;

  public AgentProxy(String masterUrl, Class<? extends AppListener> listener) {

    ActorSystem system =
        ActorSystem.create(
            "Agent",
            ConfigFactory.load("common").withValue("akka.remote.netty.tcp.port",
                ConfigValueFactory.fromAnyRef(0)));
    app = system.actorOf(Props.create(AppDriver.class, masterUrl, listener), "app");

  }

  public void requestService(ServiceRequest request) {
    app.tell(request, null);
  }

  public static void main(String[] args) {
    new AgentProxy("akka.tcp://Master@127.0.0.1:2052/user/master", null);
  }
}
