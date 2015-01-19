package com.intel.sto.bigdata.dew.service.sysmetrics.app;

import java.util.LinkedList;
import java.util.List;

public class CircleLink {
  private CircleLinkNode head;
  private CircleLinkNode tail;
  private static final int MAX_LENGTH = 100;
  private int length = 0;
  // sleep some milliseconds to wait collecting data
  private static final int WAIT_COLLECTION_TIME = 300;

  /**
   * for user API with lock
   * 
   * @return
   */
  public synchronized CircleLinkNode getHead() {
    return head;
  }

  /**
   * for dew app without lock
   * 
   * @return
   */
  public CircleLinkNode getTail() {
    return tail;
  }

  /**
   * for dew app with lock
   * 
   * @param item
   */
  public synchronized void addItem(CircleLinkNode item) {
    if (head == null) {
      head = item;
      tail = head;
      length++;
      return;
    }
    tail.setNext(item);
    tail = item;
    if (length < MAX_LENGTH) {
      length++;
      return;
    }
    head = head.getNext();
    try {
      Thread.sleep(WAIT_COLLECTION_TIME);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public synchronized List<CircleLinkNode> buildList() {
    List<CircleLinkNode> result = new LinkedList<CircleLinkNode>();
    if (head == null) {
      return result;
    }
    CircleLinkNode node = head;
    result.add(node);
    while (node.getNext() != null) {
      result.add(node.getNext());
      node = node.getNext();
    }
    return result;
  }
}
