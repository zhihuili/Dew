package com.intel.sto.bigdata.dew.http.server;

import java.io.InputStream;
import java.util.Map;

/**
 * App implement the interface to process http post data.
 *
 */
public abstract class HttpStreamCallback {

  public abstract void call(Map<String, String> parameters, InputStream is);

}
