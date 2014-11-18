package com.intel.sto.bigdata.app.webcenter.logic.ui;

import com.intel.sto.bigdata.dew.jetty.JettyCustomServer;

public class JettyServerStart {

  public static void main(String[] args) {
    // workPath is configured in start-web.sh
    String webPath = args[0];
    JettyCustomServer server = new JettyCustomServer(webPath, "/");

    // start jetty server
    server.startServer();
  }
}
