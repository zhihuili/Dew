package com.intel.sto.bigdata.dew.master;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;

public class Herse {

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
    ActorRef master = system.actorOf(Props.create(Master.class), "master");
    LoggingAdapter log = Logging.getLogger(system, master);
    log.info("Master started in " + port);
  }

}
