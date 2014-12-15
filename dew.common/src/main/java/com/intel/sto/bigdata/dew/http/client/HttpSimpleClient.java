package com.intel.sto.bigdata.dew.http.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
/**
 * Http client tool for HTTP/GET method
 *
 */
public class HttpSimpleClient {

  public static String get(String url, Map<String, String> request) throws Exception {

    String path;
    if (request != null && !request.isEmpty()) {
      StringBuilder sb = new StringBuilder(url + "?");
      for (Entry<String, String> entry : request.entrySet()) {
        sb.append(entry.getKey() + "=" + entry.getValue() + "&");
      }

      path = sb.toString().substring(0, sb.lastIndexOf("&"));
    } else {
      path = url;
    }
    return get(path);
  }

  public static String get(String url) throws Exception {
    CloseableHttpClient client = HttpClients.createDefault();
    HttpGet httpGet = new HttpGet(url);
    CloseableHttpResponse response = client.execute(httpGet);
    if (response.getStatusLine().getStatusCode() != HttpServletResponse.SC_OK) {
      throw new Exception("Http server can't process the requst successfully." + url);
    }
    HttpEntity entity = response.getEntity();
    if (entity == null) {
      throw new Exception("Http response is null." + url);
    }
    return EntityUtils.toString(entity);
  }

  /**
   * for test 
   */
  public static void main(String[] args) throws Exception {
    Map<String, String> para = new HashMap<String, String>();
    para.put("whatNodes", "LIVE");
    System.out.println(HttpSimpleClient.get("http://127.0.0.1:50070/dfsnodelist.jsp", para));
  }
}
