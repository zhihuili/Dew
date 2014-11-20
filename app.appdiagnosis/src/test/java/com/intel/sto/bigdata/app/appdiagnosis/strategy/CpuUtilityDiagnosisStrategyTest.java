package com.intel.sto.bigdata.app.appdiagnosis.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.intel.sto.bigdata.app.appdiagnosis.DiagnosisContext;
import com.intel.sto.bigdata.app.appdiagnosis.DiagnosisResult;
import com.intel.sto.bigdata.app.appdiagnosis.DiagnosisResult.Level;

import junit.framework.TestCase;

public class CpuUtilityDiagnosisStrategyTest extends DiagnosisStrategyTestCase {
  private CpuUtilityDiagnosisStrategy cpuDiagnosis;

  protected void setUp() throws Exception {
    super.setUp();
  }

  public void testDiagnose() {

    cpuDiagnosis = new CpuUtilityDiagnosisStrategy();
    result = cpuDiagnosis.diagnose(ac);

    Map<String, Level> predict = new HashMap<String, Level>();
    predict.put("sr161", Level.middle);
    predict.put("sr162", Level.high);
    predict.put("sr163", Level.low);
    predict.put("sr164", Level.low);
    predict.put("sr165", Level.middle);

    for (int i = 0; i < result.size(); i++) {
      String hostName = result.get(i).getHostName();
      Level actualResult = result.get(i).getLevel();
      Level predictResult = predict.get(hostName);
      assertEquals(predictResult, actualResult);
    }
  }

}
