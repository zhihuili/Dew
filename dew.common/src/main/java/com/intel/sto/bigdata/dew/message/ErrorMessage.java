package com.intel.sto.bigdata.dew.message;

import java.io.Serializable;

public class ErrorMessage implements Serializable {

  private static final long serialVersionUID = -8743369152490548542L;
  private String error;

  public ErrorMessage(String error) {
    this.error = error;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

}
