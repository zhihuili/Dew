package com.intel.sto.bigdata.app.appdiagnosis.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.intel.sto.bigdata.app.appdiagnosis.DiagnosisContext;
import com.intel.sto.bigdata.app.appdiagnosis.DiagnosisResult;
import com.intel.sto.bigdata.app.appdiagnosis.DiagnosisResult.Level;
import com.intel.sto.bigdata.app.appdiagnosis.util.DstatUtil;

;

/**
 * Which nodes' max cpu utility (user + sys) is low? 50% high, 70% middle, 90$ low
 * 
 */
public class CpuUtilityDiagnosisStrategy implements DiagnosisStrategy {

  @Override
  public List<DiagnosisResult> diagnose(DiagnosisContext context) {
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

      if (maxCpuUtility > 0.9) {
        tmpResult.setLevel(Level.high);
        tmpResult.setDescribe("cpu utility is high");
        tmpResult.setAdvice("null");
      } else if (maxCpuUtility > 0.7) {
        tmpResult.setLevel(Level.middle);
        tmpResult.setDescribe("cpu utility is middle");
        tmpResult.setAdvice("allocate more task on cpu properly");
      } else {
        tmpResult.setLevel(Level.low);
        tmpResult.setDescribe("cpu utility is low");
        tmpResult.setAdvice("check the calculate process");
      }

      cpuDiagnosisResult.add(tmpResult);
    }
    return cpuDiagnosisResult;
  }

}
