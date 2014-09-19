package com.intel.sto.bigdata.dew.agent;

import akka.actor.ActorSystem;
import akka.actor.Props;

import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;

public class DewDrop {

  public static void main(String[] args) {
    if (args.length < 3) {
      System.err.println("Please input master ip and port.");
      System.exit(1);
    }
    startAgent(args[0], args[1], Integer.valueOf(args[2]));
  }

  private static void startAgent(String masterIp, String masterPort, int port) {
    String url = "akka.tcp://Master@" + masterIp + ":" + masterPort + "/user/master";
    ActorSystem system =
        ActorSystem.create(
            "Agent",
            ConfigFactory.load("common").withValue("akka.remote.netty.tcp.port",
                ConfigValueFactory.fromAnyRef(port)));
    system.actorOf(Props.create(Agent.class, url), "agent");

  }

}
