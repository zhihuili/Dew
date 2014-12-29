package com.intel.sto.bigdata.app.webcenter.logic.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.intel.sto.bigdata.app.webcenter.logic.Constants;
import com.intel.sto.bigdata.app.webcenter.logic.WebCenterContext;
import com.intel.sto.bigdata.dew.http.server.JettyStreamServer;

public class InitFileServerServlet extends HttpServlet {

  private static final long serialVersionUID = 4332043419658111991L;
  private JettyStreamServer server;

  @Override
  public void init(ServletConfig config) throws ServletException {

    try {
      server = new JettyStreamServer(new FileSaveCallback());
      WebCenterContext.put(Constants.FILE_SERVER_PORT, server.getPort());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void destroy() {
    server.shutDown();
  }
}
