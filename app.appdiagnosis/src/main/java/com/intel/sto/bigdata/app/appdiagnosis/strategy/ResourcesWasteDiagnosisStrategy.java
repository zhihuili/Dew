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
 * Which nodes' computation resource(CPU, memory, disk, network) is wasted. 80% high, 50% middle,
 * 30% low
 */
public class ResourcesWasteDiagnosisStrategy implements DiagnosisStrategy {
  private static final double CPU_LOW = 30;
  private static final double CPU_MIDDLE = 50;
  private static final double CPU_HIGH = 80;

  @Override
  public List<DiagnosisResult> diagnose(DiagnosisContext context) {
    List<DiagnosisResult> wasteDiagnosisResult = new ArrayList<DiagnosisResult>();
    Map<String, List<Map<String, List<List<String>>>>> dataSet = context.getPerformanceData();

    for (String hostName : dataSet.keySet()) {

      List<Map<String, List<List<String>>>> hostDataSet = dataSet.get(hostName);
      double sumCpuUtility = 0.0;
      for (int i = 0; i < hostDataSet.size(); i++) {
        List<String> dataRecord = new ArrayList<String>();
        dataRecord = hostDataSet.get(i).get(null).get(0);
        Map<String, Double> parseResult = DstatUtil.parseDstat(dataRecord);
        sumCpuUtility += (parseResult.get("usr") + parseResult.get("sys"));
      }

      double percent = (100 * hostDataSet.size() - sumCpuUtility) / (100 * hostDataSet.size());

      DecimalFormat df = new DecimalFormat("######0.00");
      DiagnosisResult tmpResult = new DiagnosisResult();
      tmpResult.setDiagnosisName("waste-CPU");
      tmpResult.setHostName(hostName);
      percent = Double.valueOf(df.format(percent * 100));
      tmpResult.setDescribe("cpu resources waste percent is " + percent + "%");

      if (percent > CPU_HIGH) {
        tmpResult.setLevel(Level.high);
        tmpResult.setAdvice("check the cpu resources utility");
        wasteDiagnosisResult.add(tmpResult);
      } else if (percent > CPU_MIDDLE && percent < CPU_HIGH) {
        tmpResult.setLevel(Level.middle);
        tmpResult.setAdvice("add more cpu calcucate task");
        wasteDiagnosisResult.add(tmpResult);
      } else if (percent > CPU_LOW && percent < CPU_MIDDLE) {
        tmpResult.setLevel(Level.low);
        tmpResult.setAdvice("keep the cpu task allocate");
        wasteDiagnosisResult.add(tmpResult);
      }
    }

    return wasteDiagnosisResult;
  }

}
