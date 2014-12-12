package com.intel.sto.bigdata.dew.agent;

import akka.actor.ActorSystem;
import akka.actor.Props;

import com.intel.sto.bigdata.dew.utils.Host;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;

public class DewDrop {

  private static ServiceManager serviceManager = new ServiceManager();

  public static void main(String[] args) {
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

  private static void startAgent(String masterUrl, int port, String serviceDes) {
    String url;
    if (masterUrl.startsWith("akka.tcp")) {
      url = masterUrl;
    } else {
      url = "akka.tcp://Master@" + masterUrl + "/user/master";
    }
    ActorSystem system =
        ActorSystem.create(
            "Agent",
            ConfigFactory
                .load("common")
                .withValue("akka.remote.netty.tcp.port", ConfigValueFactory.fromAnyRef(port))
                .withValue("akka.remote.netty.tcp.hostname",
                    ConfigValueFactory.fromAnyRef(Host.getName())));
    system.actorOf(Props.create(Agent.class, url, serviceManager, serviceDes), "agent");
  }

  private static Thread hook = new Thread() {
    @Override
    public void run() {
      serviceManager.stopAllService();
      serviceManager.stopAllProcess();
    }
  };

}
