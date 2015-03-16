package com.intel.sto.bigdata.app.asp.chart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class Data {

  // lineName <=> workloadName

  // List<fileName>
  private List<String> fileList = new ArrayList<String>();
  // List<Map<groupName,Map<workloadName,performanceValue>>>
  private List<Map<String, Map<String, Float>>> oData =
      new ArrayList<Map<String, Map<String, Float>>>();
  // Map<groupName,Set<workloadName>>;
  private Map<String, List<String>> groupLines = new HashMap<String, List<String>>();
  // Map<groupName,List<List<%>>>
  private Map<String, List<List<Float>>> groupDetail = new HashMap<String, List<List<Float>>>();
  // Map<groupName,List<%>> deprecated
  private Map<String, List<Float>> groupAvg = new HashMap<String, List<Float>>();
  // Map<groupName,List<%>>
  private Map<String, List<Float>> newGroupAvg = new HashMap<String, List<Float>>();
  private List<String> groupList = new ArrayList<String>();
  private String dataDir;
  private String imgDir;
  private String plat;
  private String type;

  private DecimalFormat format = new DecimalFormat("###.#");

  public void buildData(Properties conf, String type) throws Exception {
    format.setRoundingMode(RoundingMode.FLOOR);
    String output = conf.getProperty("output");
    String imagedir = conf.getProperty("imagedir");
    plat = conf.getProperty("plat");
    this.type = type;
    if (type == null) {
      this.type = conf.getProperty("type");
    }
    dataDir = new File(output, plat + "." + type).getAbsolutePath();
    imgDir = new File(imagedir, plat + "." + type).getAbsolutePath();
    readFileList();
    buildOriginalPerformanceData();
    buildResultData();
  }

  /**
   * read file $output/plat.type/list
   * 
   * @throws Exception
   */
  private void readFileList() throws Exception {
    BufferedReader br =
        new BufferedReader(new InputStreamReader(new FileInputStream(new File(dataDir, "list"))));
    String line;
    while ((line = br.readLine()) != null) {
      fileList.add(line);
    }
    br.close();
  }

  /**
   * read performance files in $output/plat.type/ to build Original Performance Data
   * 
   * @throws Exception
   */
  private void buildOriginalPerformanceData() throws Exception {
    Map<String, Set<String>> groupLineSet = new HashMap<String, Set<String>>();
    for (String fileName : fileList) {
      BufferedReader br =
          new BufferedReader(
              new InputStreamReader(new FileInputStream(new File(dataDir, fileName))));
      String line;

      Map<String, Map<String, Float>> groupPerformance = new HashMap<String, Map<String, Float>>();
      while ((line = br.readLine()) != null) {
        String[] kv = line.split("=");
        String[] groupWorkload = kv[0].split("\\.");
        if (!groupPerformance.containsKey(groupWorkload[0])) {
          Map<String, Float> performance = new HashMap<String, Float>();
          performance.put(groupWorkload[1], Float.valueOf(kv[1]));
          groupPerformance.put(groupWorkload[0], performance);
        } else {
          groupPerformance.get(groupWorkload[0]).put(groupWorkload[1], Float.valueOf(kv[1]));
        }

        if (!groupLineSet.containsKey(groupWorkload[0])) {
          Set<String> lineSet = new HashSet<String>();
          lineSet.add(groupWorkload[1]);
          groupLineSet.put(groupWorkload[0], lineSet);
        } else {
          groupLineSet.get(groupWorkload[0]).add(groupWorkload[1]);
        }

      }
      br.close();
      oData.add(groupPerformance);
    }

    // Set -> List
    for (Entry<String, Set<String>> setEntry : groupLineSet.entrySet()) {
      List<String> lineList = new ArrayList<String>();
      lineList.addAll(setEntry.getValue());
      groupLines.put(setEntry.getKey(), lineList);
      groupList.add(setEntry.getKey());
    }
  }

  private void buildResultData() {
    // per group
    for (Entry<String, List<String>> groupLineEntry : groupLines.entrySet()) {
      // workload(line) per row
      List<List<Float>> detailListList = new ArrayList<List<Float>>();
      List<Float> avgList = new ArrayList<Float>();
      List<Float> newAvgList = new ArrayList<Float>();
      String groupName = groupLineEntry.getKey();
      groupDetail.put(groupName, detailListList);
      groupAvg.put(groupName, avgList);
      newGroupAvg.put(groupName, newAvgList);
      List<String> lines = groupLineEntry.getValue();
      List<Float> baseValue = null;
      Float baseAvg = 0F;
      // per day
      for (Map<String, Map<String, Float>> oDatamap : oData) {
        // current group
        Map<String, Float> performances = oDatamap.get(groupName);
        if (baseValue == null) {
          baseValue = new ArrayList<Float>();
          for (String lineName : lines) {
            Float performance = performances == null ? null : performances.get(lineName);
            if (performance == null) {
              baseValue.add(null);
            } else {
              baseAvg += performance;
              baseValue.add(performance);
            }
            detailListList.add(new ArrayList<Float>());
          }
          baseAvg = baseAvg / performances.size();
          continue;// skip the first day
        }
        Float avg = 0F;
        Float newAvg = 0F;
        int count = 0;
        for (int i = 0; i < lines.size(); i++) {
          Float performance = performances.get(lines.get(i));
          Float percentage = null;
          if (performance != null) {
            avg += performance;
            Float base = baseValue.get(i);
            if (base != null) {
              percentage = 100 * (performance - base) / base;
              newAvg += percentage;
              count++;
            } else {
              // reset base
              baseValue.set(i, performance);
            }
          }
          detailListList.get(i).add(
              percentage == null ? null : Float.valueOf(format.format(percentage)));
        }
        avg = avg / performances.size();
        if (count != 0) {
          newAvg = newAvg / count;
        } else {
          newAvg = null;
        }
        avgList.add(Float.valueOf(format.format(100 * (avg - baseAvg) / avg)));
        newAvgList.add(newAvg == null ? null : Float.valueOf(format.format(newAvg)));
      }
    }
  }

  public List<String> getFileList() {
    return fileList;
  }

  public void setFileList(List<String> fileList) {
    this.fileList = fileList;
  }

  public List<Map<String, Map<String, Float>>> getoData() {
    return oData;
  }

  public void setoData(List<Map<String, Map<String, Float>>> oData) {
    this.oData = oData;
  }

  public Map<String, List<String>> getGroupLines() {
    return groupLines;
  }

  public void setGroupLines(Map<String, List<String>> groupLines) {
    this.groupLines = groupLines;
  }

  public Map<String, List<List<Float>>> getGroupDetail() {
    return groupDetail;
  }

  public void setGroupDetail(Map<String, List<List<Float>>> groupDetail) {
    this.groupDetail = groupDetail;
  }

  public Map<String, List<Float>> getGroupAvg() {
    return groupAvg;
  }

  public void setGroupAvg(Map<String, List<Float>> groupAvg) {
    this.groupAvg = groupAvg;
  }

  public String getPlat() {
    return plat;
  }

  public void setPlat(String plat) {
    this.plat = plat;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDataDir() {
    return dataDir;
  }

  public void setDataDir(String dataDir) {
    this.dataDir = dataDir;
  }

  public String getImgDir() {
    return imgDir;
  }

  public void setImgDir(String imgDir) {
    this.imgDir = imgDir;
  }

  public List<String> getGroupList() {
    return groupList;
  }

  public void setGroupList(List<String> groupList) {
    this.groupList = groupList;
  }

  public Map<String, List<Float>> getNewGroupAvg() {
    return newGroupAvg;
  }

  public void setNewGroupAvg(Map<String, List<Float>> newGroupAvg) {
    this.newGroupAvg = newGroupAvg;
  }

}
