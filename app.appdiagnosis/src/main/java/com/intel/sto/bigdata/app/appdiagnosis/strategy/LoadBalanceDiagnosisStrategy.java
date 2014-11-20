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
  private static final double LOW = 20;
  private static final double MIDDLE = 35;
  private static final double HIGH = 50;

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
      String performanceIndex) {
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
      double percent = (avgLoadNum - avgLoad.get(host)) / avgLoadNum;
      if (percent > LOW) {
        DiagnosisResult tmpResult = new DiagnosisResult();
        tmpResult.setDiagnosisName(performanceIndex);
        tmpResult.setHostName(host);

        percent = Double.valueOf(df.format(percent * 100));
        tmpResult.setDescribe(performanceIndex + " is lower than cluster average by " + percent
            + "%");
        tmpResult.setAdvice("Check the node or your application algorism.");
        if (percent > HIGH) {
          tmpResult.setLevel(Level.high);
        } else if (percent > MIDDLE && percent < HIGH) {
          tmpResult.setLevel(Level.middle);
        } else if (percent < MIDDLE) {
          tmpResult.setLevel(Level.low);
        }
        result.add(tmpResult);
      }
    }

    return result;
  }

}
