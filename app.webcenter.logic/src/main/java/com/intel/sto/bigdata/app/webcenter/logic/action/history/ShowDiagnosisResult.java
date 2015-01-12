package com.intel.sto.bigdata.app.webcenter.logic.action.history;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

public class ShowDiagnosisResult extends ActionSupport {
  private static final long serialVersionUID = 5374459682419912155L;
  private String path;
  private List<List<String>> result;

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public List<List<String>> getResult() {
    return result;
  }

  public void setResult(List<List<String>> result) {
    this.result = result;
  }

  public String execute() throws Exception {
    path += "/analysis.result";

    result = readCSVFile(path);

    return SUCCESS;
  }

  public static List<List<String>> readCSVFile(String path) throws Exception {
    File file = new File(path);
    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
    List<List<String>> lines = new ArrayList<List<String>>();
    String line;
    while ((line = br.readLine()) != null) {
      List<String> row = new ArrayList<String>();
      String[] array = line.split("\\|");
      for (String item : array) {
        row.add(item);
      }
      lines.add(row);
    }
    br.close();
    return lines;
  }
}
