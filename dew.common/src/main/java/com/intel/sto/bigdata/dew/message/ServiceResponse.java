package com.intel.sto.bigdata.dew.message;

import java.io.Serializable;

public class ServiceResponse implements Serializable {

  private static final long serialVersionUID = 6547146362222187075L;
  private String content;
  private ErrorMessage em;

  public ServiceResponse() {
  }

  public ServiceResponse(String content) {
    this.content = content;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public ErrorMessage getEm() {
    return em;
  }

  public void setEm(ErrorMessage em) {
    this.em = em;
  }

}
