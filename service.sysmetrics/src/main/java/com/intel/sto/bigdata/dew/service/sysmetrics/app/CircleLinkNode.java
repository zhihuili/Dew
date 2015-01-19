package com.intel.sto.bigdata.dew.service.sysmetrics.app;

import java.util.Map;

public class CircleLinkNode {
  private Map<String, String> element;
  private CircleLinkNode next;
  private long timestamp;

  public CircleLinkNode(Map<String, String> element, long timestamp) {
    this.element = element;
    this.timestamp = timestamp;
  }

  public Map<String, String> getElement() {
    return element;
  }

  public void setElement(Map<String, String> element) {
    this.element = element;
  }

  public CircleLinkNode getNext() {
    return next;
  }

  public void setNext(CircleLinkNode next) {
    this.next = next;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public String toString() {
    return timestamp + ":" + element.toString();
  }

}
