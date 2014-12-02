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
import com.intel.sto.bigdata.app.appdiagnosis.util.Constants;

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

    loadMetrics.put(Constants.DSTAT_USR, "load-CPU");
    loadMetrics.put(Constants.DSTAT_USED, "load-Mem");
    loadMetrics.put(Constants.DSTAT_TOTALRECV, "load-Net-Recv");
    loadMetrics.put(Constants.DSTAT_TOTALSEND, "load-Net-Send");
    loadMetrics.put(Constants.DSTAT_DISKREAD, "load-Disk-Read");
    loadMetrics.put(Constants.DSTAT_DISKWRIT, "load-Disk-Writ");

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
        dataRecord = hostDataSet.get(i).get(Constants.NULL).get(0);
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
      double percent =
          Double.valueOf(df.format((avgLoadNum - avgLoad.get(host)) / avgLoadNum * 100));
      if (percent > LOW) {
        DiagnosisResult tmpResult = new DiagnosisResult();
        tmpResult.setDiagnosisName(performanceIndex);
        tmpResult.setHostName(host);

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
