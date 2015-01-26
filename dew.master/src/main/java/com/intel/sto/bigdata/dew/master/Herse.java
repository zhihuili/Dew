package com.intel.sto.bigdata.dew.master;

import static java.util.concurrent.TimeUnit.SECONDS;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.pattern.Patterns;
import akka.util.Timeout;

import com.intel.sto.bigdata.dew.actor.ActorDef;
import com.intel.sto.bigdata.dew.actor.DewSupervisor;
import com.intel.sto.bigdata.dew.utils.Host;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;

public class Herse {
  private static FiniteDuration duration = Duration.create(3, SECONDS);

  public static void main(String[] args) throws Exception {
    if (args.length < 1) {
      System.err.println("Please input master port.");
      System.exit(1);
    }
    startMaster(Integer.valueOf(args[0]));
  }

  private static void startMaster(int port) throws Exception {
    ActorSystem system =
        ActorSystem.create(
            "Master",
            ConfigFactory
                .load("common")
                .withValue("akka.remote.netty.tcp.port", ConfigValueFactory.fromAnyRef(port))
                .withValue("akka.remote.netty.tcp.hostname",
                    ConfigValueFactory.fromAnyRef(Host.getName())));
    ActorRef supervisor = system.actorOf(Props.create(DewSupervisor.class), "dew");
    ActorRef master =
        (ActorRef) Await.result(Patterns.ask(supervisor, new ActorDef(Props.create(Master.class),
            "master"), new Timeout(duration)), duration);

    LoggingAdapter log = Logging.getLogger(system, master);
    log.info("Master started in " + port);
  }

}
