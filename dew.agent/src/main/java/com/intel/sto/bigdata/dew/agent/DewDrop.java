package com.intel.sto.bigdata.dew.agent;

import static java.util.concurrent.TimeUnit.SECONDS;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;

import com.intel.sto.bigdata.dew.actor.ActorDef;
import com.intel.sto.bigdata.dew.actor.DewSupervisor;
import com.intel.sto.bigdata.dew.utils.Host;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;

public class DewDrop {
  private static FiniteDuration duration = Duration.create(3, SECONDS);
  private static ServiceManager serviceManager = new ServiceManager();

  public static void main(String[] args) throws Exception {
    if (args.length < 1) {
      System.err.println("Please input master url.");
      System.exit(1);
    }
    if (args.length == 1) {
      startAgent(args[0], 0, null);
    } else {
      startAgent(args[0], 0, args[1]);
    }
    Runtime.getRuntime().addShutdownHook(hook);
  }

  private static void startAgent(String masterUrl, int port, String serviceDes) throws Exception {
    String url;
    if (masterUrl.startsWith("akka.tcp")) {
      url = masterUrl;
    } else {
      url = "akka.tcp://Master@" + masterUrl + "/user/dew/master";
    }
    ActorSystem system =
        ActorSystem.create(
            "Agent",
            ConfigFactory
                .load("common")
                .withValue("akka.remote.netty.tcp.port", ConfigValueFactory.fromAnyRef(port))
                .withValue("akka.remote.netty.tcp.hostname",
                    ConfigValueFactory.fromAnyRef(Host.getName())));
    ActorRef supervisor = system.actorOf(Props.create(DewSupervisor.class), "dew");
    Await.result(Patterns.ask(supervisor,
        new ActorDef(Props.create(Agent.class, url, serviceManager, serviceDes), "agent"),
        new Timeout(duration)), duration);
  }

  private static Thread hook = new Thread() {
    @Override
    public void run() {
      serviceManager.stopAllService();
      serviceManager.stopAllProcess();
    }
  };

}
