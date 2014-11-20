package com.intel.sto.bigdata.app.appdiagnosis.strategy;

import com.intel.sto.bigdata.app.appdiagnosis.DiagnosisResult.Level;

import junit.framework.TestCase;

public class LoadBalanceDiagnosisStrategyTest extends DiagnosisStrategyTestCase {
  private LoadBalanceDiagnosisStrategy loadDiagnosis;

  protected void setUp() throws Exception {
    super.setUp();
  }

  public void testDiagnose() {
    loadDiagnosis = new LoadBalanceDiagnosisStrategy();
    result = loadDiagnosis.diagnose(ac);

    assertEquals(Level.low, result.get(8).getLevel());
    assertEquals(Level.high, result.get(5).getLevel());
    assertEquals(Level.middle, result.get(12).getLevel());
    assertEquals(Level.middle, result.get(11).getLevel());
    assertEquals(Level.high, result.get(1).getLevel());
    assertEquals(Level.high, result.get(5).getLevel());
  }

}
