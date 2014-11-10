package com.intel.sto.bigdata.app.appdiagnosis;

public class DiagnosisResult {

  private String diagnosisName;
  private String hostName;
  private Level level;
  private String describe;
  private String advice;

  public String getDiagnosisName() {
    return diagnosisName;
  }

  public void setDiagnosisName(String analysisName) {
    this.diagnosisName = analysisName;
  }

  public Level getLevel() {
    return level;
  }

  public void setLevel(Level level) {
    this.level = level;
  }

  public String getDescribe() {
    return describe;
  }

  public void setDescribe(String describe) {
    this.describe = describe;
  }

  public String getAdvice() {
    return advice;
  }

  public void setAdvice(String advice) {
    this.advice = advice;
  }

  public String getHostName() {
    return hostName;
  }

  public void setHostName(String hostName) {
    this.hostName = hostName;
  }

  public enum Level {
    high, middle, low;
  }

  @Override
  public String toString() {
    return "|" + hostName + " | " + diagnosisName + " | " + level + " | " + describe + " | "
        + advice + " |";
  }

  public static String getHead() {
    return "|hostName | diagnosisName | level | describe | advice|";
  }
}
