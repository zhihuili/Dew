package com.intel.sto.bigdata.dew.http.server;

import java.io.FileInputStream;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.xml.XmlConfiguration;

public class JettyCustomServer extends Server {

  private String resourceBase;
  private String webXmlPath;
  private String contextPath;

  public JettyCustomServer(String resourceBase, String contextPath) {

    super();

    this.resourceBase = resourceBase;
    this.contextPath = contextPath;
    webXmlPath = resourceBase + "/WEB-INF/web.xml";

    readXmlConfig();

    applyHandle();
  }

  private void readXmlConfig() {
    try {
      XmlConfiguration configuration =
          new XmlConfiguration(new FileInputStream(resourceBase + "/jetty.xml"));
      configuration.configure(this);
    } catch (Exception e1) {
      e1.printStackTrace();
    }
  }

  public void applyHandle() {

    ContextHandlerCollection handler = new ContextHandlerCollection();

    WebAppContext webapp = new WebAppContext();
    webapp.setContextPath(contextPath);
    webapp.setResourceBase(resourceBase);
    webapp.setDescriptor(webXmlPath);

    handler.addHandler(webapp);
    super.setHandler(handler);
  }

  // start jetty server
  public void startServer() {
    try {
      super.start();
      System.out.println("current thread:" + super.getThreadPool().getThreads() + "| idle thread:"
          + super.getThreadPool().getIdleThreads());
      super.join();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
