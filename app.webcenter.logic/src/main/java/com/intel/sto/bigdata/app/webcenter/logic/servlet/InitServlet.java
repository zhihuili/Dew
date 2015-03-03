package com.intel.sto.bigdata.app.webcenter.logic.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.intel.sto.bigdata.app.webcenter.logic.Constants;
import com.intel.sto.bigdata.app.webcenter.logic.WebCenterContext;
import com.intel.sto.bigdata.app.webcenter.logic.action.bean.JobBean;
import com.intel.sto.bigdata.app.webcenter.logic.db.DBOperator;
import com.intel.sto.bigdata.app.webcenter.logic.timer.Timer;
import com.intel.sto.bigdata.dew.app.driver.MyAppDriver;
import com.intel.sto.bigdata.dew.http.server.JettyStreamServer;
import com.intel.sto.bigdata.dew.service.sysmetrics.app.CircleLink;
import com.intel.sto.bigdata.dew.service.sysmetrics.app.GroupMetricsApp;
import com.intel.sto.bigdata.dew.utils.Files;

public class InitServlet extends HttpServlet {

  private static final long serialVersionUID = 4332043419658111991L;
  private JettyStreamServer server;
  private GroupMetricsApp dstatApp;
  private static final int DSTAT_INTERVAL = 2000;
  private Logger log = Logger.getLogger(InitServlet.class);

  @Override
  public void init(ServletConfig config) throws ServletException {

    try {
      // init file stream http server
      server = new JettyStreamServer(new FileSaveCallback());
      WebCenterContext.put(Constants.FILE_SERVER_PORT, server.getPort());

      // init WebCenterContext
      String dewHome = System.getenv("DEW_HOME");
      Map<String, String> conf = Files.loadPropertiesFile("/conf.properties.default");
      File confFile = new File(dewHome, "app.webcenter/conf.properties");
      if (confFile.exists()) {
        System.out.println("Load conf from " + confFile.getAbsolutePath());
        Map<String, String> userConf = Files.loadPropertiesFile(new FileInputStream(confFile));
        conf.putAll(userConf);
      }
      WebCenterContext.putConf(conf);

      // init cluster system performance data for home page
      CircleLink cl = new CircleLink();
      dstatApp = GroupMetricsApp.getInstance();
      dstatApp.startCollectCulsterDstat(cl, DSTAT_INTERVAL);
      WebCenterContext.put(Constants.DSTAT_CIRCLELINK, cl);

      // init quartz scheduler
      Timer timer = Timer.getInstance();
      DBOperator operator = new DBOperator();
      ArrayList<JobBean> jobs = operator.getAllJob();
      for (JobBean job : jobs) {
        timer.schedule(job.getName(), job.getCycle());
      }
    } catch (Exception e) {
      log.error(e.getMessage());

    }
  }

  @Override
  public void destroy() {
    server.shutDown();
    MyAppDriver.getMyAppDriver().stop();
  }
}
