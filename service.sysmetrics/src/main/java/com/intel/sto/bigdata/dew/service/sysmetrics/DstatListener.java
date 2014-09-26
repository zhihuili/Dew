package com.intel.sto.bigdata.dew.service.sysmetrics;

import com.intel.sto.bigdata.dew.app.AppListener;

public class DstatListener extends AppListener {

  @Override
  public void process() {
    System.out.println("processed---" + responseList);
  }

}
