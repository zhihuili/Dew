package com.intel.sto.bigdata.app.appdiagnosis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.intel.sto.bigdata.app.appdiagnosis.strategy.DiagnosisStrategy;
import com.intel.sto.bigdata.app.appdiagnosis.util.FileUtil;

public class Diagnostician {

  public static List<DiagnosisResult> diagnose(DiagnosisContext context) throws Exception {
    List<DiagnosisResult> result = new ArrayList<DiagnosisResult>();
    for (DiagnosisStrategy as : FileUtil.loadAnalysisStrategy()) {
      List<DiagnosisResult> list = as.diagnose(context);
      if (list != null) {
        result.addAll(list);
      }
    }
    return result;
  }

}
