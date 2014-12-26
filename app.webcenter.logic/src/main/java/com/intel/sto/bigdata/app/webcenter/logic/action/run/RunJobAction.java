package com.intel.sto.bigdata.app.webcenter.logic.action.run;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.intel.sto.bigdata.app.webcenter.logic.Constants;
import com.intel.sto.bigdata.app.webcenter.logic.db.DBOperator;
import com.intel.sto.bigdata.dew.app.AgentProxy;
import com.intel.sto.bigdata.dew.app.AppDes;
import com.intel.sto.bigdata.dew.app.DoNothingAppProcessor;
import com.intel.sto.bigdata.dew.service.shellexecutor.message.ExecuteRequest;
import com.intel.sto.bigdata.dew.utils.Files;
import com.intel.sto.bigdata.dew.utils.Host;
import com.opensymphony.xwork2.ActionSupport;
import com.intel.sto.bigdata.app.webcenter.logic.action.bean.*;

public class RunJobAction extends ActionSupport {
  public String name;
  public DBOperator operator = new DBOperator();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String execute() throws Exception {
    String hostName = Host.getName();
    JobBean jobBean = operator.getSingleJobByName(name);
    String recordId = operator.addNewJobRecord(jobBean.jobId);

    AppBean appBean = operator.getSingleAppByName(jobBean.defination);
    ExecuteRequest request = new ExecuteRequest();
    request.setId(recordId);
    request.setDirectory(appBean.path);
    request.setCommand(appBean.executable);
    request.setStatusUrl("http://" + hostName + ":6077/JobEnd.action");
    Set<String> hosts = new HashSet<String>();
    hosts.add(appBean.host);

    Map<String, String> conf = new HashMap<String, String>();
    conf = Files.loadPropertiesFile("/conf.properties");
    String masterUrl = conf.get(Constants.DEW_MASTER);

    new AgentProxy(masterUrl, new DoNothingAppProcessor(), new AppDes(hosts, "shell"))
        .requestService(request);

    return SUCCESS;
  }
}