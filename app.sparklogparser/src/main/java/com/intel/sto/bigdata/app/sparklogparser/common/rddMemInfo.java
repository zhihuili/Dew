package com.intel.sto.bigdata.app.sparklogparser.common;

public class rddMemInfo {
  long time = 0;
  float memSize = 0;

  public rddMemInfo(long inputTime, float inputMemSize) {
    time = inputTime;
    memSize = inputMemSize;
  }

  public long getTime() {
    return time;
  }

  public float getMemSize() {
    return memSize;
  }

  public void addMemSize(float addSize) {
    memSize += addSize;
  }

  public void setMemSize(float setMemSize) {
    memSize = setMemSize;
  }

  public void setTime(long setTime) {
    time = setTime;
  }
}
