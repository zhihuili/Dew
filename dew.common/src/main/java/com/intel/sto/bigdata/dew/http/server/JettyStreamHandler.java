package com.intel.sto.bigdata.dew.http.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.intel.sto.bigdata.dew.utils.Util;

public class JettyStreamHandler extends AbstractHandler {
  private HttpStreamCallback cb;

  public JettyStreamHandler(HttpStreamCallback callback) {
    cb = callback;
  }

  @Override
  public void handle(String target, Request baseRequest, HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {
    Map<String, String> parameters = new HashMap<String, String>();
    for (Enumeration<String> e = request.getHeaderNames(); e.hasMoreElements();) {
      String key = e.nextElement().toString();
      String name = Util.removePrefix(key);
      if (name != null) {
        String value = request.getHeader(key);
        parameters.put(name, value);
      }
    }
    InputStream is = request.getInputStream();
    cb.call(parameters, is);
    response.setStatus(HttpServletResponse.SC_OK);
    baseRequest.setHandled(true);
  }

}
