package com.intel.sto.bigdata.app.appdiagnosis.strategy;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.intel.sto.bigdata.app.appdiagnosis.DiagnosisContext;
import com.intel.sto.bigdata.app.appdiagnosis.DiagnosisResult;
import com.intel.sto.bigdata.app.appdiagnosis.DiagnosisResult.Level;
import com.intel.sto.bigdata.app.appdiagnosis.util.DstatUtil;

/**
 * Which nodes' load is lower than average? 50% high, 35% middle, 20%
 * 
 */
public class LoadBalanceDiagnosisStrategy implements DiagnosisStrategy {

  @Override
  public List<DiagnosisResult> diagnose(DiagnosisContext context) {
    List<DiagnosisResult> loadDiagnosisResult = new ArrayList<DiagnosisResult>();
    Map<String, List<Map<String, List<List<String>>>>> dataSet = context.getPerformanceData();

    Map<String, String> loadMetrics = new HashMap<String, String>();

    loadMetrics.put("usr", "load-CPU");
    loadMetrics.put("used", "load-Mem");
    loadMetrics.put("totalrecv", "load-Net-Recv");
    loadMetrics.put("totalsend", "load-Net-Send");
    loadMetrics.put("diskread", "load-Disk-Read");
    loadMetrics.put("diskwrit", "load-Disk-Writ");

    String metricsArray[] = DstatUtil.metricsHead;
    for (String metrics : loadMetrics.keySet()) {
      if (Arrays.asList(metricsArray).contains(metrics)) {
        ArrayList<DiagnosisResult> tmpResult =
            diagnosisForEachLoad(dataSet, metrics, loadMetrics.get(metrics));
        loadDiagnosisResult.addAll(tmpResult);
      }
    }

    return loadDiagnosisResult;
  }

  public static ArrayList<DiagnosisResult> diagnosisForEachLoad(
      Map<String, List<Map<String, List<List<String>>>>> dataSet, String loadName,
      String diagnosisName) {
    DecimalFormat df = new DecimalFormat("######0.00");
    ArrayList<DiagnosisResult> result = new ArrayList<DiagnosisResult>();

    Map<String, Double> avgLoad = new HashMap<String, Double>();
    for (String hostName : dataSet.keySet()) {
      List<Map<String, List<List<String>>>> hostDataSet = dataSet.get(hostName);
      double sumLoad = 0.0;
      for (int i = 0; i < hostDataSet.size(); i++) {
        List<String> dataRecord = new ArrayList<String>();
        dataRecord = hostDataSet.get(i).get(null).get(0);
        Map<String, Double> parseResult = DstatUtil.parseDstat(dataRecord);
        sumLoad += parseResult.get(loadName);
      }
      avgLoad.put(hostName, sumLoad / hostDataSet.size());
    }

    double avgLoadNum = 0.0;
    for (String nodeName : avgLoad.keySet()) {
      avgLoadNum += avgLoad.get(nodeName);
    }
    avgLoadNum = avgLoadNum / avgLoad.size();

    for (String host : avgLoad.keySet()) {
      if (avgLoad.get(host) - avgLoadNum < 0) {
        DiagnosisResult tmpResult = new DiagnosisResult();
        tmpResult.setDiagnosisName(diagnosisName);
        tmpResult.setHostName(host);
        double percent = (avgLoadNum - avgLoad.get(host)) / avgLoadNum;
        if (percent >= 0.5) {
          tmpResult.setLevel(Level.high);
          tmpResult.setDescribe(diagnosisName + " is low than avg by " + df.format(percent * 100)
              + "%");
          tmpResult.setAdvice("add task more on " + diagnosisName);
        } else if (percent >= 0.35) {
          tmpResult.setLevel(Level.middle);
          tmpResult.setDescribe(diagnosisName + " is low than avg by " + df.format(percent * 100)
              + "%");
          tmpResult.setAdvice("add task on " + diagnosisName);
        } else {
          tmpResult.setLevel(Level.low);
          tmpResult.setDescribe(diagnosisName + " is low than avg by " + df.format(percent * 100)
              + "%");
          tmpResult.setAdvice("add task less on " + diagnosisName);
        }
        result.add(tmpResult);
      }
    }

    return result;
  }

}
