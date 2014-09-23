package com.intel.sto.bigdata.dew.message;

public class ServiceMessage {
  private String content;

  public ServiceMessage() {
  }

  public ServiceMessage(String content) {
    this.content = content;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

}
