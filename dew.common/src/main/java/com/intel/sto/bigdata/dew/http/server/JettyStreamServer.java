package com.intel.sto.bigdata.dew.http.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jetty.server.Server;

/**
 * Start a http server on random port for client post request include stream.<br>
 * Request parameter transfered by http head.
 */
public class JettyStreamServer {
  Server server;
  int port;

  public JettyStreamServer(HttpStreamCallback cb) throws Exception {
    server = new Server(0);
    server.setHandler(new JettyStreamHandler(cb));
    server.start();
    port = server.getConnectors()[0].getLocalPort();
  }

  public int getPort() {
    return port;
  }

  public void shutDown() {
    try {
      server.stop();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // for test
  public static void main(String[] args) throws Exception {

    JettyStreamServer server = new JettyStreamServer(new HttpStreamCallback() {
      @Override
      public void call(Map<String, String> parameters, InputStream is) {
        for (Entry<String, String> entry : parameters.entrySet()) {
          System.out.println("name:" + entry.getKey() + "  value:" + entry.getValue());
        }
        try {
          String c;
          BufferedReader br = new BufferedReader(new InputStreamReader(is));
          while ((c = br.readLine()) != null) {
            System.out.println(c);
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });

    System.out.println("HTTP server start at:" + server.getPort());
  }
}
