package com.intel.sto.bigdata.dew.master;

import akka.actor.ActorSystem;
import akka.actor.Props;

import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;

public class Heres {

  public static void main(String[] args) {
    if (args.length < 1) {
      System.err.println("Please input master port.");
      System.exit(1);
    }
    startMaster(Integer.valueOf(args[0]));
  }

  private static void startMaster(int port) {
    ActorSystem system =
        ActorSystem.create(
            "Master",
            ConfigFactory.load("common").withValue("akka.remote.netty.tcp.port",
                ConfigValueFactory.fromAnyRef(port)));
    system.actorOf(Props.create(Master.class), "master");
    System.out.println("Master started in " + port);
  }

}
