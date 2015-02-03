package com.intel.sto.bigdata.app.sparkreport.report;

import java.util.List;

public class OutputBuilder {
  private static OutputBuilder instance = new OutputBuilder();

  public static OutputBuilder getInstance() {
    return instance;
  }

  public StringBuilder build(List<Result> resultList) {
    StringBuilder out = new StringBuilder();
    out.append("<table>");
    out.append("<tr><td>App Name</td><td>Start Time</td><td>End Time</td><td>Duration</td><td>Exception Message</td></tr>");
    for (Result result : resultList) {
      out.append("<tr>");
      out.append("<td>" + result.getAppName() + "</td>");
      out.append("<td>" + result.getStartTime() + "</td>");
      out.append("<td>" + result.getEndTime() + "</td>");
      out.append("<td>" + result.getDuration() + "</td>");
      out.append("<td>" + result.getExcepitonMessage() + "</td>");
      out.append("</tr>");
    }
    out.append("</table>");
    return out;
  }

  public void printSystem(StringBuilder out) {
    System.out.println(out.toString());
  }

  public void sendEmail(StringBuilder out) {

  }

  public void saveFile(StringBuilder out) {

  }
}
