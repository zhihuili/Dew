package com.intel.sto.bigdata.dew.service.sysmetrics.app;

import java.util.Map;

import akka.actor.ActorRef;

import com.intel.sto.bigdata.dew.app.AppDes;
import com.intel.sto.bigdata.dew.app.driver.MyAppDriver;
import com.intel.sto.bigdata.dew.message.AgentRegister;

public class GroupMetricsApp {
  private MyAppDriver driver;
  private Map<AgentRegister, ActorRef> agentActors;
  private CollectionCurrentDstatThread thread;

  private static GroupMetricsApp instance;

  private GroupMetricsApp() throws Exception {
    driver = MyAppDriver.getMyAppDriver();
    agentActors = driver.getAgents(new AppDes(null, "dstat"));

  }

  public synchronized static GroupMetricsApp getInstance() throws Exception {
    if (instance == null) {
      instance = new GroupMetricsApp();
    }
    return instance;
  }

  public void startCollectCulsterDstat(CircleLink cl, int interval) {
    ActorRef groupActor = driver.createActor(GroupActor.class, "groupMetricsApp", cl, agentActors);
    thread = new CollectionCurrentDstatThread(groupActor, interval);
    thread.start();
  }
  
  public void stop(){
    thread.stopCllect();
  }
}
