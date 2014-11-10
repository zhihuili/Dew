package com.intel.sto.bigdata.app.sparklogparser.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author frank
 * 
 * @param <T1> Child
 * @param <T2> Parent
 */
public abstract class Node<T1, T2> {
  private List<T1> children;
  private String str;
  private Node<T1, T2> parent;
  private long startTime;
  private long endTime;
  private String name;
  private String id;
  private String duration;
  private long appStartTime;
  private long appEndTime;

  public List<T1> getChildren() {
    return children;
  }

  public void setChildren(List<T1> children) {
    this.children = children;
  }

  public String getStr() {
    return str;
  }

  public void setStr(String str) {
    this.str = str;
  }

  public Node<T1, T2> getParent() {
    return parent;
  }

  public void setParent(Node<T1, T2> parent) {
    this.parent = parent;
  }

  public long getStartTime() {
    return startTime;
  }

  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }

  public long getEndTime() {
    return endTime;
  }

  public void setEndTime(long endTime) {
    this.endTime = endTime;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDuration() {
    return duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }

  public long getAppStartTime() {
    return appStartTime;
  }

  public void setAppStartTime(long appStartTime) {
    this.appStartTime = appStartTime;
  }

  public long getAppEndTime() {
    return appEndTime;
  }

  public void setAppEndTime(long appEndTime) {
    this.appEndTime = appEndTime;
  }

  public T1 getLastChild() throws Exception {
    if (children == null || children.isEmpty()) {
      throw new Exception(this + " hasn't child, can't be process.");
    }
    return children.get(children.size() - 1);
  }

  public T1 getFirstChild() throws Exception {
    if (children == null || children.isEmpty()) {
      throw new Exception(this + " hasn't child, can't get first child.");
    }
    return children.get(0);
  }

  public void addNewChild(T1 t) {
    if (children == null) {
      children = new ArrayList<T1>();
    }
    children.add(t);
  }
}
