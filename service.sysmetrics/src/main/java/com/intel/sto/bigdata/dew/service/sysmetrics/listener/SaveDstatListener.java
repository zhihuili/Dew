package com.intel.sto.bigdata.dew.service.sysmetrics.listener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.intel.sto.bigdata.dew.app.AgentProxy;
import com.intel.sto.bigdata.dew.app.AppDes;
import com.intel.sto.bigdata.dew.app.AppProcessor;
import com.intel.sto.bigdata.dew.message.ServiceResponse;
import com.intel.sto.bigdata.dew.service.sysmetrics.SaveDstatFile;
import com.intel.sto.bigdata.dew.service.sysmetrics.message.DstatServiceRequest;

public class SaveDstatListener implements AppProcessor {

  private String path;
  private Set<String> hosts;

  public SaveDstatListener(String path, Set<String> hosts) {
    this.path = path;
    this.hosts = hosts;
  }

  @Override
  public void process(List<ServiceResponse> list) {
    for (ServiceResponse response : list) {
      String hostName =
          hosts.contains(response.getNodeName()) ? response.getNodeName() : response.getIp();
      SaveDstatFile.save(path, hostName, response.getContent());
    }
  }

  /**
   * for test
   * 
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    if (args.length < 2) {
      System.err.println("Please input master url and hostName.");
      System.exit(1);
    }

    Set<String> hosts = new HashSet<String>();
    hosts.add(args[1]);
    SaveDstatListener sdl = new SaveDstatListener("/tmp/", hosts);

    new AgentProxy(args[0], sdl, new AppDes(hosts, "dstat"))
        .requestService(new DstatServiceRequest(System.currentTimeMillis() - 3000, System
            .currentTimeMillis() - 1000));
  }
}
