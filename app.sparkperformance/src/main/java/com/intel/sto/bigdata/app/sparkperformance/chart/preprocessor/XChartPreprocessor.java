package com.intel.sto.bigdata.app.sparkperformance.chart.preprocessor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.intel.sto.bigdata.app.sparkperformance.Constants;
import com.intel.sto.bigdata.app.sparkperformance.Util;
import com.intel.sto.bigdata.app.sparkperformance.WorkloadConf;
import com.intel.sto.bigdata.app.sparkperformance.WorkloadContext;
import com.intel.sto.bigdata.app.sparkperformance.des.CommandDes;
import com.intel.sto.bigdata.app.sparkperformance.des.Regex;

public class XChartPreprocessor {

  /**
   * Store data for each command as 4-D list
   */
  String csvFolder;
  long startTime = 0l;
  long remainder;
  List<Map<String, List<List<String>>>> dataList = new ArrayList<Map<String, List<List<String>>>>();

  public void setCSVFolder(String csvFolder) {
    this.csvFolder = csvFolder;
  }

  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }

  public long getRemainder() {
    return remainder;
  }

  private void readFile(CommandDes des, String currentSlave) {
    String fileName = csvFolder + des.getCommandName() + "_" + currentSlave + ".dat";
    System.out.println(fileName);
    try {
      FileReader fr = new FileReader(fileName);
      BufferedReader br = new BufferedReader(fr);
      String line = null;
      int i = 0;
      while (i < des.getStartSkip()) {
        br.readLine();
        i++;
      }
      long time = (startTime - (Long) WorkloadContext.get(Constants.WORKLOAD_RUNTIME)) / 1000;
      // System.out.println("JOB START AT: " + startTime);
      // FIXME add timestamp to get more accurate results
      if (time > 0 && startTime > 0) {
        long num = time / des.getOwnFrequency();
        long freq = des.getOwnFrequency();
        if (time % freq != 0) {
          num += 1;
          remainder = freq - time % freq;
        } else
          remainder = 0;
        System.out.println("Time offset: " + time + "\tDiscard " + num + "lines\t" + "Remainder: "
            + remainder);
        i = 0;
        line = br.readLine();
        while (i < num) {
          if (des.getGroupDes().get(0).getGroupName().equals("null")) {
            i++;
            if (i == num)
              break;
            line = br.readLine();
          } else if (line.equals("")) {
            for (int m = 0; m < des.getGroupDes().size(); m++) {
              while (true) {
                // System.out.println("HAHA: "+ line);
                line = br.readLine();
                if (line.equals(""))
                  break;
              }
            }
            i++;
          }
        }
      }

      if (line == null || (line != null && !line.equals("")))
        line = br.readLine();
      while (true) {
        if (line.equals("")) {
          Map<String, List<List<String>>> map = new HashMap<String, List<List<String>>>();
          for (int m = 0; m < des.getGroupDes().size(); m++) {
            List<List<String>> groupTable = new ArrayList<List<String>>();
            String split = des.getGroupDes().get(m).getSplit();
            Regex regex = des.getGroupDes().get(m).getRegex();
            String regexValue = des.getGroupDes().get(m).getRegexValue();
            line = br.readLine();
            // System.out.println("REGEX LINE: "+line);
            if (line == null) {
              break;
            }
            if (checkRegex(line, regex, regexValue)) {
              // System.out.println("HEHE"+line);
              line = br.readLine();
              while (line != null && !line.equals("")) {
                // System.out.println("mama" + line);
                List<String> record = Util.getList(line, split);
                line = br.readLine();
                groupTable.add(record);
              }
            } else {
              br.close();
              throw new Exception("Wrong regex: " + regex + "----" + line);
            }
            map.put(des.getGroupDes().get(m).getGroupName(), groupTable);
          }
          if (line != null)
            dataList.add(map);
        } else if (des.getGroupDes().get(0).getGroupName().equals("null")) {
          Map<String, List<List<String>>> map = new HashMap<String, List<List<String>>>();
          List<List<String>> table = new ArrayList<List<String>>();
          String split = des.getGroupDes().get(0).getSplit();
          List<String> record = Util.getList(line, split);
          table.add(record);
          map.put("null", table);
          line = br.readLine();
          dataList.add(map);
        } else {
          br.close();
          System.out.println("Line: " + line);
          throw new Exception("Cannot process the file format");
        }
        if (line == null) {
          br.close();
          break;
        }
      }
      br.close();
    } catch (Exception e) {
      System.out.println(fileName + " is not exists or destroyed. Skip the file.");
      return;
    }
  }

  private boolean checkRegex(String line, Regex regex, String regexValue) {
    if (regex.equals(Regex.indexOf) && line.indexOf(regexValue) != -1)
      return true;
    else if (regex.equals(Regex.startWith) && line.startsWith(regexValue))
      return true;
    return false;
  }

  public List<Map<String, List<List<String>>>> getDataList(CommandDes cd, String slave)
      throws Exception {
    readFile(cd, slave);
    System.out.println("dataList Size: " + dataList.size());
    // for(int i = 0; i < dataList.size(); i++){
    // Map<String,List<List<String>>> map = dataList.get(i);
    // for(int j = 0 ; j < cd.getGroupDes().size(); j++){
    // System.out.println("Group Name: " + cd.getGroupDes().get(j).getGroupName());
    // List<List<String>> table = map.get(cd.getGroupDes().get(j).getGroupName());
    // for(int m = 0; m < table.size(); m++){
    // List<String> record = table.get(m);
    // for(int n = 0 ; n < table.get(0).size(); n++)
    // System.out.print(record.get(n) + " ");
    // System.out.println();
    // }
    // }
    // }
    return dataList;
  }
}
