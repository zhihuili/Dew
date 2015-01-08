package com.intel.sto.bigdata.app.webcenter.logic.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.intel.sto.bigdata.app.webcenter.logic.Constants;
import com.intel.sto.bigdata.app.webcenter.logic.WebCenterContext;
import com.intel.sto.bigdata.dew.http.server.JettyStreamServer;
import com.intel.sto.bigdata.dew.utils.Files;

public class InitServlet extends HttpServlet {

  private static final long serialVersionUID = 4332043419658111991L;
  private JettyStreamServer server;

  @Override
  public void init(ServletConfig config) throws ServletException {

    try {
      server = new JettyStreamServer(new FileSaveCallback());
      WebCenterContext.put(Constants.FILE_SERVER_PORT, server.getPort());
      String dewHome = System.getenv("DEW_HOME");
      Map<String, String> conf = Files.loadPropertiesFile("/conf.properties.default");
      File confFile = new File(dewHome, "app.webcenter/conf.properties");
      if (confFile.exists()) {
        System.out.println("Load conf from " + confFile.getAbsolutePath());
        Map<String, String> userConf = Files.loadPropertiesFile(new FileInputStream(confFile));
        conf.putAll(userConf);
      }
      WebCenterContext.putConf(conf);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void destroy() {
    server.shutDown();
  }
}
