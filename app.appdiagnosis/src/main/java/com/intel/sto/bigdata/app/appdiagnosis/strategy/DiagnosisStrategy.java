package com.intel.sto.bigdata.app.appdiagnosis.strategy;

import java.util.List;

import com.intel.sto.bigdata.app.appdiagnosis.DiagnosisContext;
import com.intel.sto.bigdata.app.appdiagnosis.DiagnosisResult;

public interface DiagnosisStrategy {

  List<DiagnosisResult> diagnose(DiagnosisContext context);
}
