package com.intel.sto.bigdata.dew.conf;

import java.io.Serializable;
import java.util.Properties;

public class DewConfMessage implements Serializable {

  private static final long serialVersionUID = 5040454979655858597L;
  private Properties p;

  public Properties getP() {
    return p;
  }

  public void setP(Properties p) {
    this.p = p;
  }

}
