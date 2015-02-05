package com.intel.sto.bigdata.app.sparkreport.report;

import java.util.List;

import com.intel.sto.bigdata.dew.utils.Host;

public class OutputBuilder {
  private static String webPort = "6077";
  private static OutputBuilder instance = new OutputBuilder();

  public static OutputBuilder getInstance() {
    return instance;
  }

  public StringBuilder build(List<Result> resultList) {
    StringBuilder out = new StringBuilder();
    out.append("<table border=\"1\" style=\"width:100%\">");
    out.append("<tr><th>App Name</th><th>Start Time</th><th>End Time</th><th>Duration(ms)</th><th>Exception Message</th><th>Detail</th></tr>");
    for (Result result : resultList) {
      out.append("<tr>");
      out.append("<td>" + result.getAppName() + "</td>");
      out.append("<td>" + result.getStartTime() + "</td>");
      out.append("<td>" + result.getEndTime() + "</td>");
      out.append("<td>" + result.getDuration() + "</td>");
      out.append("<td>" + result.getExceptionMessage() + "</td>");
      out.append(buildDetail(result.getAppId()));
      out.append("</tr>");
    }
    out.append("</table>");
    return out;
  }

  private StringBuffer buildDetail(String appId) {
    StringBuffer sb = new StringBuffer();
    String hostName = Host.getName();
    sb.append("<td>");
    sb.append("<a href='http://" + hostName + ":" + webPort + "/action/showAnalysisResult?enginID="
        + appId + "'>Performance</a>");
    sb.append("<br>");
    sb.append("<a href='http://" + hostName + ":" + webPort + "/action/logQuery?appRecordId=" + appId
        + "'>LogQuery</a>");
    sb.append("<br>");
    sb.append("<a href='http://" + hostName + ":" + webPort
        + "/action/showDiagnosisResult?enginID=" + appId + "'>Diagnosis</a>");
    sb.append("</td>");
    return sb;
  }

  public void printSystem(StringBuilder out) {
    System.out.println(out.toString());
  }

  public void sendEmail(StringBuilder out) {

  }

  public void saveFile(StringBuilder out) {

  }
}
