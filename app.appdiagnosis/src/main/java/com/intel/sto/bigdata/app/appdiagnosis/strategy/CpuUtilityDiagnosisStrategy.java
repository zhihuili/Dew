package com.intel.sto.bigdata.app.appdiagnosis.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.intel.sto.bigdata.app.appdiagnosis.DiagnosisContext;
import com.intel.sto.bigdata.app.appdiagnosis.DiagnosisResult;
import com.intel.sto.bigdata.app.appdiagnosis.DiagnosisResult.Level;
import com.intel.sto.bigdata.app.appdiagnosis.util.DstatUtil;

/**
 * Which nodes' max cpu utility (user + sys) is low? 50% high, 70% middle, 90$ low
 * 
 */
public class CpuUtilityDiagnosisStrategy implements DiagnosisStrategy {
  private static final double CPU_LOW = 50;
  private static final double CPU_MIDDLE = 70;
  private static final double CPU_HIGH = 90;

  @Override
  public List<DiagnosisResult> diagnose(DiagnosisContext context) {
    // TODO Eliminate CPU sample glitch
    List<DiagnosisResult> cpuDiagnosisResult = new ArrayList<DiagnosisResult>();
    Map<String, List<Map<String, List<List<String>>>>> dataSet = context.getPerformanceData();
    for (String hostName : dataSet.keySet()) {

      List<Map<String, List<List<String>>>> hostDataSet = dataSet.get(hostName);
      double maxCpuUtility = 0.0;
      for (int i = 0; i < hostDataSet.size(); i++) {
        List<String> dataRecord = new ArrayList<String>();
        dataRecord = hostDataSet.get(i).get(null).get(0);
        Map<String, Double> parseResult = DstatUtil.parseDstat(dataRecord);
        double cpuUtility = parseResult.get("usr") + parseResult.get("sys");
        maxCpuUtility = (maxCpuUtility < cpuUtility) ? cpuUtility : maxCpuUtility;
      }

      DiagnosisResult tmpResult = new DiagnosisResult();
      tmpResult.setDiagnosisName("CPU-utility");
      tmpResult.setHostName(hostName);

      if (maxCpuUtility < CPU_LOW) {
        tmpResult.setLevel(Level.high);
        tmpResult.setDescribe("Max CPU utility has some problems.");
        tmpResult
            .setAdvice("Increase executors number or max core number for the application, or increase RDD's partition number.");
        cpuDiagnosisResult.add(tmpResult);
      } else if (maxCpuUtility > CPU_LOW && maxCpuUtility < CPU_MIDDLE) {
        tmpResult.setLevel(Level.middle);
        tmpResult.setDescribe("Max CPU utility is more less than average.");
        tmpResult.setAdvice("allocate more task on cpu properly");
        cpuDiagnosisResult.add(tmpResult);
      } else if (maxCpuUtility > CPU_MIDDLE && maxCpuUtility < CPU_HIGH) {
        tmpResult.setLevel(Level.low);
        tmpResult.setDescribe("Max CPU utility is less than normal.");
        tmpResult.setAdvice("keep CPU calcucate process.");
        cpuDiagnosisResult.add(tmpResult);
      }
    }
    return cpuDiagnosisResult;
  }

}
