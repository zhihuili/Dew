package com.intel.sto.bigdata.app.webcenter.logic.action.cluster;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.log4j.Logger;

import net.sf.json.JSONSerializer;

import com.intel.sto.bigdata.app.appdiagnosis.util.DstatUtil;
import com.intel.sto.bigdata.app.webcenter.logic.Constants;
import com.intel.sto.bigdata.app.webcenter.logic.WebCenterContext;
import com.intel.sto.bigdata.dew.service.sysmetrics.app.CircleLink;
import com.intel.sto.bigdata.dew.service.sysmetrics.app.CircleLinkNode;
import com.opensymphony.xwork2.ActionSupport;

public class GetClusterData extends ActionSupport {
  private static final long serialVersionUID = 3820407751289666665L;

  private String jsonCPU;
  private String jsonMEM;
  private String jsonNETWORK;
  private String jsonDISK;
  private String jsonTIME;

  private static Logger log = Logger.getLogger(GetClusterData.class);

  public String getJsonTIME() {
    return jsonTIME;
  }

  public void setJsonTIME(String jsonTIME) {
    this.jsonTIME = jsonTIME;
  }

  public String getJsonCPU() {
    return jsonCPU;
  }

  public void setJsonCPU(String jsonCPU) {
    this.jsonCPU = jsonCPU;
  }

  public String getJsonMEM() {
    return jsonMEM;
  }

  public void setJsonMEM(String jsonMEM) {
    this.jsonMEM = jsonMEM;
  }

  public String getJsonNETWORK() {
    return jsonNETWORK;
  }

  public void setJsonNETWORK(String jsonNETWORK) {
    this.jsonNETWORK = jsonNETWORK;
  }

  public String getJsonDISK() {
    return jsonDISK;
  }

  public void setJsonDISK(String jsonDISK) {
    this.jsonDISK = jsonDISK;
  }

  public String execute() throws Exception {
    CircleLink cl = (CircleLink) WebCenterContext.get(Constants.DSTAT_CIRCLELINK);
    List<CircleLinkNode> dstatList = cl.buildList();

    List<String> timeStamp = new ArrayList<String>();
    List<ChartData> tableCPU = new ArrayList<ChartData>();
    ChartData dataCPUUsr = new ChartData();
    dataCPUUsr.setName("usr");
    ChartData dataCPUSys = new ChartData();
    dataCPUSys.setName("sys");
    ChartData dataCPUIdl = new ChartData();
    dataCPUIdl.setName("idl");
    ChartData dataCPUWai = new ChartData();
    dataCPUWai.setName("wai");
    List<ChartData> tableMEM = new ArrayList<ChartData>();
    ChartData dataMEMUsed = new ChartData();
    dataMEMUsed.setName("used");
    ChartData dataMEMBuffer = new ChartData();
    dataMEMBuffer.setName("buffer");
    ChartData dataMEMCached = new ChartData();
    dataMEMCached.setName("cached");
    ChartData dataMEMFree = new ChartData();
    dataMEMFree.setName("Free");
    List<ChartData> tableNETWORK = new ArrayList<ChartData>();
    ChartData dataNETWORKRecv = new ChartData();
    dataNETWORKRecv.setName("totalrecv");
    ChartData dataNETWORKSend = new ChartData();
    dataNETWORKSend.setName("totalsend");
    List<ChartData> tableDISK = new ArrayList<ChartData>();
    ChartData dataDiskRead = new ChartData();
    dataDiskRead.setName("diskread");
    ChartData dataDiskWrit = new ChartData();
    dataDiskWrit.setName("diskwrit");

    List<Integer> metricUsr = new ArrayList<Integer>();
    List<Integer> metricSys = new ArrayList<Integer>();
    List<Integer> metricIdl = new ArrayList<Integer>();
    List<Integer> metricWai = new ArrayList<Integer>();

    List<Integer> metricUsed = new ArrayList<Integer>();
    List<Integer> metricBuffer = new ArrayList<Integer>();
    List<Integer> metricCached = new ArrayList<Integer>();
    List<Integer> metricFree = new ArrayList<Integer>();

    List<Integer> metricTotalrecv = new ArrayList<Integer>();
    List<Integer> metricTotalsend = new ArrayList<Integer>();

    List<Integer> metricDiskread = new ArrayList<Integer>();
    List<Integer> metricDiskWrit = new ArrayList<Integer>();

    for (CircleLinkNode node : dstatList) {
      Map<String, String> map = node.getElement();
      if (map == null || map.isEmpty()) {
        continue;
      }
      long timestamp = node.getTimestamp();
      Date date = new Date(timestamp);
      DateFormat df = DateFormat.getTimeInstance();
      String currentTime = df.format(date);
      timeStamp.add(currentTime);

      double avgCPUUsr = 0.0;
      double avgCPUSys = 0.0;
      double avgCPUIdl = 0.0;
      double avgCPUWai = 0.0;
      double avgMEMUsed = 0.0;
      double avgMEMBuffer = 0.0;
      double avgMEMCached = 0.0;
      double avgMEMFree = 0.0;
      double avgNETWORKRecv = 0.0;
      double avgNETWORKSend = 0.0;
      double avgDISKRead = 0.0;
      double avgDISKWrit = 0.0;
      for (Entry<String, String> e : map.entrySet()) {
        String dstatLine = e.getValue();
        Map<String, Double> dstat = DstatUtil.parseDstat(dstatLine.split(","));
        avgCPUUsr += dstat.get("usr");
        avgCPUSys += dstat.get("sys");
        avgCPUIdl += dstat.get("idl");
        avgCPUWai += dstat.get("wai");

        avgMEMUsed += dstat.get("used");
        avgMEMBuffer += dstat.get("buffer");
        avgMEMCached += dstat.get("cached");
        avgMEMFree += dstat.get("free");

        avgNETWORKRecv += dstat.get("totalrecv");
        avgNETWORKSend += dstat.get("totalsend");

        avgDISKRead += dstat.get("diskread");
        avgDISKWrit += dstat.get("diskwrit");
      }
      avgCPUUsr = avgCPUUsr / map.size();
      avgCPUSys = avgCPUSys / map.size();
      avgCPUIdl = avgCPUIdl / map.size();
      avgCPUWai = avgCPUWai / map.size();
      avgMEMUsed = avgMEMUsed / map.size();
      avgMEMBuffer = avgMEMBuffer / map.size();
      avgMEMCached = avgMEMCached / map.size();
      avgMEMFree = avgMEMFree / map.size();
      avgNETWORKRecv = avgNETWORKRecv / map.size();
      avgNETWORKSend = avgNETWORKSend / map.size();
      avgDISKRead = avgDISKRead / map.size();
      avgDISKWrit = avgDISKWrit / map.size();

      metricUsr.add((int)Math.floor(avgCPUUsr));
      metricSys.add((int)Math.floor(avgCPUSys));
      metricIdl.add((int)Math.floor(avgCPUIdl));
      metricWai.add((int)Math.floor(avgCPUWai));

      metricUsed.add((int)Math.floor(avgMEMUsed));
      metricBuffer.add((int)Math.floor(avgMEMBuffer));
      metricCached.add((int)Math.floor(avgMEMCached));
      metricFree.add((int)Math.floor(avgMEMFree));

      metricTotalrecv.add((int)Math.floor(avgNETWORKRecv));
      metricTotalsend.add((int)Math.floor(avgNETWORKSend));

      metricDiskread.add((int)Math.floor(avgDISKRead));
      metricDiskWrit.add((int)Math.floor(avgDISKWrit));
    }

    while (timeStamp.size() != 100) {
      metricUsr.add(null);
      metricSys.add(null);
      metricIdl.add(null);
      metricWai.add(null);

      metricUsed.add(null);
      metricBuffer.add(null);
      metricCached.add(null);
      metricFree.add(null);

      metricTotalrecv.add(null);
      metricTotalsend.add(null);

      metricDiskread.add(null);
      metricDiskWrit.add(null);
      timeStamp.add(null);
    }

    dataCPUUsr.setData(metricUsr);
    dataCPUSys.setData(metricSys);
    dataCPUIdl.setData(metricIdl);
    dataCPUWai.setData(metricWai);

    dataMEMUsed.setData(metricUsed);
    dataMEMBuffer.setData(metricBuffer);
    dataMEMCached.setData(metricCached);
    dataMEMFree.setData(metricFree);

    dataNETWORKRecv.setData(metricTotalrecv);
    dataNETWORKSend.setData(metricTotalsend);

    dataDiskRead.setData(metricDiskread);
    dataDiskWrit.setData(metricDiskWrit);

    tableCPU.add(dataCPUUsr);
    tableCPU.add(dataCPUSys);
    tableCPU.add(dataCPUIdl);
    tableCPU.add(dataCPUWai);

    tableMEM.add(dataMEMUsed);
    tableMEM.add(dataMEMBuffer);
    tableMEM.add(dataMEMCached);
    tableMEM.add(dataMEMFree);

    tableNETWORK.add(dataNETWORKSend);
    tableNETWORK.add(dataNETWORKRecv);

    tableDISK.add(dataDiskRead);
    tableDISK.add(dataDiskWrit);

    jsonCPU = JSONSerializer.toJSON(tableCPU).toString();
    jsonMEM = JSONSerializer.toJSON(tableMEM).toString();
    jsonNETWORK = JSONSerializer.toJSON(tableNETWORK).toString();
    jsonDISK = JSONSerializer.toJSON(tableDISK).toString();
    jsonTIME = JSONSerializer.toJSON(timeStamp).toString();

    return "success";
  }
}
