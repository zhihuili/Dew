package com.intel.sto.bigdata.app.webcenter.logic.action.run;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.intel.sto.bigdata.app.webcenter.logic.Constants;
import com.intel.sto.bigdata.app.webcenter.logic.WebCenterContext;
import com.intel.sto.bigdata.app.webcenter.logic.action.bean.AppBean;
import com.intel.sto.bigdata.app.webcenter.logic.action.bean.JobBean;
import com.intel.sto.bigdata.app.webcenter.logic.db.DBOperator;
import com.intel.sto.bigdata.dew.app.AgentProxy;
import com.intel.sto.bigdata.dew.app.AppDes;
import com.intel.sto.bigdata.dew.app.DoNothingAppProcessor;
import com.intel.sto.bigdata.dew.service.shellexecutor.message.ExecuteRequest;
import com.intel.sto.bigdata.dew.utils.Host;
import com.opensymphony.xwork2.ActionSupport;

public class RunJobAction extends ActionSupport {

  private static final long serialVersionUID = 8610596211473803872L;
  private String name;
  private DBOperator operator = new DBOperator();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String execute() throws Exception {
    String hostName = Host.getName();
    JobBean jobBean = operator.getSingleJobByName(name);
    String jobrecordId = operator.addNewJobRecord(jobBean.getJobId());

    String defination = jobBean.getDefination();
    ArrayList<String> appList = getApps(defination);

    for (String app : appList) {
      AppBean appBean = operator.getSingleAppByName(app);
      String appRecordID = operator.addNewAppRecord(appBean, jobrecordId);
      ExecuteRequest request = new ExecuteRequest();
      request.setId(appRecordID);
      request.setDirectory(appBean.getPath());
      request.setCommand(appBean.getExecutable());
      request.setStatusUrl("http://" + hostName + ":6077/action/JobEnd.action");
      request.setLogUrl("http://" + hostName + ":"
          + WebCenterContext.get(Constants.FILE_SERVER_PORT));

      Set<String> hosts = new HashSet<String>();
      hosts.add(appBean.getHost());
      Map<String, String> conf = WebCenterContext.getConf();
      String masterUrl = conf.get(Constants.DEW_MASTER);

      new AgentProxy(masterUrl, new DoNothingAppProcessor(), new AppDes(hosts, "shell"))
          .requestService(request);
    }

    return SUCCESS;
  }

  private ArrayList<String> getApps(String defination) {
    ArrayList<String> result = new ArrayList<String>();

    String tmp[] = defination.split(",");

    for (String app : tmp) {
      result.add(app);
    }

    return result;
  }
}