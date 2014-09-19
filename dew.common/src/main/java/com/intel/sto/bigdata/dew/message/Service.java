package com.intel.sto.bigdata.dew.message;

import java.io.InputStream;
import java.io.OutputStream;

public abstract class Service implements Runnable {

  abstract void stop();

  OutputStream get() {
    return null;
  }

  void post(InputStream is) {
  }

}
