package com.intel.sto.bigdata.app.appdiagnosis.strategy;

import java.util.HashMap;
import java.util.Map;

import com.intel.sto.bigdata.app.appdiagnosis.DiagnosisResult.Level;

import junit.framework.TestCase;

public class ResourcesWasteDiagnosisStrategyTest extends DiagnosisStrategyTestCase {
  private ResourcesWasteDiagnosisStrategy wasteDiagnosis;

  protected void setUp() throws Exception {
    super.setUp();
  }

  public void testDiagnose() {
    wasteDiagnosis = new ResourcesWasteDiagnosisStrategy();
    result = wasteDiagnosis.diagnose(ac);

    Map<String, Level> predict = new HashMap<String, Level>();
    predict.put("sr165", Level.middle);
    predict.put("sr162", Level.middle);
    predict.put("sr161", Level.high);

    for (int i = 0; i < result.size(); i++) {
      String hostName = result.get(i).getHostName();
      Level actualResult = result.get(i).getLevel();
      Level predictResult = predict.get(hostName);
      assertEquals(predictResult, actualResult);
    }
  }
}
