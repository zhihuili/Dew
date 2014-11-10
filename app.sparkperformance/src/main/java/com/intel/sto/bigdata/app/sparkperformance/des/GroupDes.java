package com.intel.sto.bigdata.app.sparkperformance.des;

import java.util.ArrayList;
import java.util.List;

public class GroupDes {
  String groupName = null;
  Regex regex = null;
  String regexValue = null;
  List<String> headDes = new ArrayList<String>();
  String split;

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public void setRegex(Regex regex) {
    this.regex = regex;
  }

  public void setRegexValue(String value) {
    this.regexValue = value;
  }

  public void setHeadDes(List<String> headDes) {
    this.headDes = headDes;
  }

  public void setSplit(String split) {
    this.split = split;
  }

  public String getGroupName() {
    return groupName;
  }

  public Regex getRegex() {
    return regex;
  }

  public String getRegexValue() {
    return regexValue;
  }

  public List<String> getHeadDes() {
    return headDes;
  }

  public String getSplit() {
    return split;
  }
}
