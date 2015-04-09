package com.intel.sto.bigdata.dew.service.sysmetrics.web;

import java.io.File;

import com.intel.sto.bigdata.dew.http.server.JettyCustomServer;
import com.intel.sto.bigdata.dew.service.Service;
import com.intel.sto.bigdata.dew.service.sysmetrics.app.CircleLink;
import com.intel.sto.bigdata.dew.utils.Util;

public class WebService extends Service {
  LocalMetrics metrics;
  JettyCustomServer server;

  @Override
  public void run() {

    CircleLink cl = CircleLink.getInstance();
    metrics = new LocalMetrics();
    metrics.startCollectCulsterDstat(cl, 2000);

    String dewHome = Util.getDewHome();
    File file = new File(dewHome, "service.sysmetrics/webapp");
    server = new JettyCustomServer(file.getAbsolutePath(), "/");

    // start jetty server
    server.startServer();

  }

  @Override
  public void stop() {
    metrics.stop();
    try {
      server.stop();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
