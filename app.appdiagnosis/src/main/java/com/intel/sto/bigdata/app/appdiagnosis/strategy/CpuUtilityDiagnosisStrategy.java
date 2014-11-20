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

  @Override
  public List<DiagnosisResult> diagnose(DiagnosisContext context) {
    // TODO Eliminate CPU sample glitch
    List<DiagnosisResult> cpuDiagnosisResult = new ArrayList<DiagnosisResult>();
    Map<String, List<Map<String, List<List<String>>>>> dataSet = context.getPerformanceData();
    for (String hostName : dataSet.keySet()) {
      DiagnosisResult tmpResult = new DiagnosisResult();

      tmpResult.setDiagnosisName("CPU");
      tmpResult.setHostName(hostName);
      List<Map<String, List<List<String>>>> hostDataSet = dataSet.get(hostName);
      double maxCpuUtility = 0.0;
      for (int i = 0; i < hostDataSet.size(); i++) {
        List<String> dataRecord = new ArrayList<String>();
        dataRecord = hostDataSet.get(i).get(null).get(0);
        Map<String, Double> parseResult = DstatUtil.parseDstat(dataRecord);
        double cpuUtility = parseResult.get("usr") + parseResult.get("sys");
        maxCpuUtility = (maxCpuUtility < cpuUtility) ? cpuUtility : maxCpuUtility;
      }

      if (maxCpuUtility > 90) {
        tmpResult.setLevel(Level.low);
        tmpResult.setDescribe("cpu utility problem is low");
        tmpResult.setAdvice("keep the state");
      } else if (maxCpuUtility > 70) {
        tmpResult.setLevel(Level.middle);
        tmpResult.setDescribe("cpu utility problem is middle");
        tmpResult.setAdvice("allocate more task on cpu properly");
      } else {
        tmpResult.setLevel(Level.high);
        tmpResult.setDescribe("cpu utility problem is high");
        tmpResult.setAdvice("check the calculate process");
      }

      cpuDiagnosisResult.add(tmpResult);
    }
    return cpuDiagnosisResult;
  }

}
