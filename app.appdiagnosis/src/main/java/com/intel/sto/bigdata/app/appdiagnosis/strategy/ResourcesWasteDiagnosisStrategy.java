package com.intel.sto.bigdata.app.appdiagnosis.strategy;

import java.util.List;

import com.intel.sto.bigdata.app.appdiagnosis.DiagnosisContext;
import com.intel.sto.bigdata.app.appdiagnosis.DiagnosisResult;

/**
 * Which nodes' computation resource(CPU, memory, disk, network) is wasted. 80% high, 50% middle,
 * 30% low
 */
public class ResourcesWasteDiagnosisStrategy implements DiagnosisStrategy {

  @Override
  public List<DiagnosisResult> diagnose(DiagnosisContext context) {
    // TODO Auto-generated method stub
    return null;
  }

}
