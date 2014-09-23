package com.intel.sto.bigdata.dew.app;

import akka.actor.ActorSystem;
import akka.actor.Props;

import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;

public class AgentProxy {

  public AgentProxy(String masterUrl) {

    ActorSystem system =
        ActorSystem.create(
            "Agent",
            ConfigFactory.load("common").withValue("akka.remote.netty.tcp.port",
                ConfigValueFactory.fromAnyRef(0)));
    system.actorOf(Props.create(App.class, masterUrl), "app");

  }

  public static void main(String[] args) {
    new AgentProxy("akka.tcp://Master@127.0.0.1:2052/user/master");
  }
}
