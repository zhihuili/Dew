package com.intel.sto.bigdata.app.appdiagnosis.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.intel.sto.bigdata.app.appdiagnosis.DiagnosisResult;
import com.intel.sto.bigdata.app.appdiagnosis.strategy.DiagnosisStrategy;
import com.intel.sto.bigdata.dew.utils.Files;

public class FileUtil {

  private FileUtil() {
  }

  public static List<DiagnosisStrategy> loadAnalysisStrategy() throws Exception {
    Set<String> lines = Files.loadResourceFile(FileUtil.class.getResourceAsStream("/diagnosis.strategies"));
    ClassLoader cl = FileUtil.class.getClassLoader();
    List<DiagnosisStrategy> strategies = new ArrayList<DiagnosisStrategy>();
    for (String line : lines) {
      strategies.add((DiagnosisStrategy) cl.loadClass(line).newInstance());
    }
    return strategies;
  }

  public static void printAnalysisResult(List<DiagnosisResult> ars, File analysisFile)
      throws Exception {
    FileOutputStream fos = new FileOutputStream(analysisFile);
    fos.write(DiagnosisResult.getHead().getBytes());
    fos.write("\r\n".getBytes());
    for (DiagnosisResult ar : ars) {
      fos.write(ar.toString().getBytes());
      fos.write("\r\n".getBytes());
    }
    fos.flush();
    fos.close();
  }

  public static void main(String[] args) throws Exception {
    for (DiagnosisStrategy as : loadAnalysisStrategy()) {
      System.out.println(as);
    }
  }
}
