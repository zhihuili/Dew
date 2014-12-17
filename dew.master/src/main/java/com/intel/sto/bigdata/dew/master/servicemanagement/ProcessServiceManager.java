package com.intel.sto.bigdata.dew.master.servicemanagement;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import akka.actor.ActorRef;

import com.intel.sto.bigdata.dew.master.ClusterState;
import com.intel.sto.bigdata.dew.message.AgentRegister;
import com.intel.sto.bigdata.dew.service.ServiceDes;
import com.intel.sto.bigdata.dew.utils.Constants;

/**
 * Scan agents, start process service.
 *
 */
public class ProcessServiceManager extends Thread {

  private List<ServiceDes> defaultServices;
  private ActorRef master;
  // Start one process service per 50 nodes.
  // TODO It should be configured for every service.
  private final int perNodes = 50;
  // waiting for agent registration
  private final int sleepTime = 10 * 1000;
  private boolean initialized = false;

  public ProcessServiceManager(List<ServiceDes> services, ActorRef master) {
    this.defaultServices = services;
    this.master = master;
  }

  @Override
  public void run() {
    try {
      Thread.sleep(sleepTime);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    initialized = true;
    scan();
  }

  /**
   * scan defaultServices and cluster, find nodes for process service.
   */
  public synchronized void scan() {
    if (!initialized) {
      return;
    }
    for (ServiceDes sd : defaultServices) {
      if (Constants.PROCESS_SERVICE_TYPE.equals(sd.getServiceType())) {
        Set<String> processNodes = new HashSet<String>();
        Set<String> agentNodes = new HashSet<String>();
        for (AgentRegister ar : ClusterState.findAgent(null, null)) {
          if (ar.getServices().contains(sd.getServiceName())) {
            processNodes.add(ar.getHostName());
          } else {
            agentNodes.add(ar.getHostName());
          }
        }
        agentNodes.removeAll(processNodes);

        Set<String> newProcessNodes = findNewProcessNodes(agentNodes, processNodes.size());
        Set<AgentRegister> newProcessAgents = ClusterState.findAgent(newProcessNodes, null);
        for (AgentRegister ar : newProcessAgents) {
          ar.getAgent().tell(sd, master);
        }
      }
    }
  }

  private Set<String> findNewProcessNodes(Set<String> nodes, int startedProcessNum) {
    Set<String> result = new HashSet<String>();

    int expectedNodeNum = (int) Math.ceil(nodes.size() / perNodes);
    int neededProcessNum = 0;
    if (expectedNodeNum < 2) {
      neededProcessNum = 2;// at least start 2 process for HA.
    } else {
      neededProcessNum = expectedNodeNum;
    }
    if (startedProcessNum >= neededProcessNum) {
      return result;
    }
    int newProcessNum = neededProcessNum - startedProcessNum;
    if (nodes.size() <= newProcessNum) {
      return nodes;
    }
    // TODO use load balance replace random
    for (int i = 0; i < newProcessNum; i++) {
      int randomNum = (int) (Math.random() * nodes.size());
      int j = 0;
      String newNode = null;
      for (String node : nodes) {
        if (j == randomNum) {
          newNode = node;
          break;
        }
        j++;
      }
      if (newNode != null) {
        result.add(newNode);
        nodes.remove(newNode);
      }
    }
    return result;
  }
}
