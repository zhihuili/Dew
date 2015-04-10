package com.intel.sto.bigdata.app.appdiagnosis.strategy;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.intel.sto.bigdata.app.appdiagnosis.DiagnosisContext;
import com.intel.sto.bigdata.app.appdiagnosis.DiagnosisResult;
import com.intel.sto.bigdata.app.appdiagnosis.DiagnosisResult.Level;
import com.intel.sto.bigdata.app.appdiagnosis.util.Constants;
import com.intel.sto.bigdata.dew.utils.DstatUtil;

/**
 * Which nodes' computation resource(CPU) is wasted. 80% high, 50% middle,30% low
 * calcaute the area of cpu utility(usr+sys) , the rest of area on the area chart is the resourcesWaste
 * compare the resourcesWaste with set value , output result
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
        dataRecord = hostDataSet.get(i).get(Constants.NULL).get(0);
        Map<String, Double> parseResult = DstatUtil.parseDstat(dataRecord);
        //calculate the area cpu utility on area chart
        sumCpuUtility +=
            (parseResult.get(Constants.DSTAT_USR) + parseResult.get(Constants.DSTAT_SYS));
      }

      //the rest area on chart is resourcesWaste
      double percent = 1 - sumCpuUtility / (100 * hostDataSet.size());

      DecimalFormat df = new DecimalFormat("######0.00");
      DiagnosisResult tmpResult = new DiagnosisResult();
      tmpResult.setDiagnosisName("waste-CPU");
      tmpResult.setHostName(hostName);
      double percentage = Double.valueOf(df.format(percent * 100));
      tmpResult.setDescribe("Cpu resources waste percent is " + percentage
          + "%. More time on non-computation task.");
      tmpResult.setAdvice("Improve node's disk and network performance.");
      //compare the resourcesWaste with set value , output result
      if (percentage > CPU_HIGH) {
        tmpResult.setLevel(Level.high);
        wasteDiagnosisResult.add(tmpResult);
      } else if (percentage > CPU_MIDDLE && percentage < CPU_HIGH) {
        tmpResult.setLevel(Level.middle);
        wasteDiagnosisResult.add(tmpResult);
      } else if (percentage > CPU_LOW && percentage < CPU_MIDDLE) {
        tmpResult.setLevel(Level.low);
        wasteDiagnosisResult.add(tmpResult);
      }
    }

    return wasteDiagnosisResult;
  }

}
