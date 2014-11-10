package com.intel.sto.bigdata.app.appdiagnosis.strategy;

import java.util.List;

import com.intel.sto.bigdata.app.appdiagnosis.DiagnosisContext;
import com.intel.sto.bigdata.app.appdiagnosis.DiagnosisResult;

/**
 * Which nodes' max cpu utility (user + sys) is low? 50% high, 70% middle, 90$ low
 * 
 */
public class CpuUtilityDiagnosisStrategy implements DiagnosisStrategy {

  @Override
  public List<DiagnosisResult> diagnose(DiagnosisContext context) {
    return null;
  }

}
