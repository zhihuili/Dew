package com.intel.sto.bigdata.dew.message;

import java.io.Serializable;

public class ServiceResponse implements Serializable {

  private static final long serialVersionUID = 6547146362222187075L;
  private String nodeName;
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

  public String getNodeName() {
    return nodeName;
  }

  public void setNodeName(String nodeName) {
    this.nodeName = nodeName;
  }

  public boolean hasException() {
    return this.em != null;
  }

}
