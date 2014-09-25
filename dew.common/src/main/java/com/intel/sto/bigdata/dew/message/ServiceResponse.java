package com.intel.sto.bigdata.dew.message;

public class ServiceResponse {
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
