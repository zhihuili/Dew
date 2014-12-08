package com.intel.sto.bigdata.dew.http.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.intel.sto.bigdata.dew.utils.Util;

/**
 * Http client post stream data to http server.<br>
 * Request parameter transfered by http head.
 *
 */
public class HttpStreamClient {

  public static void post(String url, Map<String, String> parameters, InputStream is)
      throws Exception {
    HttpPost httpPost = new HttpPost(url);
    for (Entry<String, String> e : parameters.entrySet()) {
      httpPost.addHeader(Util.addPrefix(e.getKey()), e.getValue());
    }
    InputStreamEntity entity = new InputStreamEntity(is);
    httpPost.setEntity(entity);
    CloseableHttpClient httpClient = HttpClients.createDefault();
    try {
      CloseableHttpResponse response = httpClient.execute(httpPost);
      if (response.getStatusLine().getStatusCode() != HttpServletResponse.SC_OK) {
        throw new Exception("Http server can't process the post requst successfully." + url);
      }
    } catch (Exception e1) {
      e1.printStackTrace();
    } finally {
      httpClient.close();
    }
  }

  // for test
  public static void main(String[] args) throws Exception {
    Map<String, String> map = new HashMap<String, String>();
    map.put("tester", "who am i?");
    HttpStreamClient.post("http://127.0.0.1:54033", map, new FileInputStream(new File("pom.xml")));
  }
}
