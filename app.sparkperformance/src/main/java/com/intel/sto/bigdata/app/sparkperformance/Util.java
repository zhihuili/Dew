package com.intel.sto.bigdata.app.sparkperformance;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.intel.sto.bigdata.app.sparkperformance.des.ChartDes;
import com.intel.sto.bigdata.app.sparkperformance.des.ChartProcessType;
import com.intel.sto.bigdata.app.sparkperformance.des.ChartType;
import com.intel.sto.bigdata.app.sparkperformance.des.CommandDes;
import com.intel.sto.bigdata.app.sparkperformance.des.GroupDes;
import com.intel.sto.bigdata.app.sparkperformance.des.Regex;

public class Util {

  public static void runCmd(String cmd) throws Exception {
    String[] exe = { "/bin/sh", "-c", cmd };
    if (Runtime.getRuntime().exec(exe).waitFor() != 0) {
      throw new Exception("Failed to execute:" + cmd);
    }
  }

  public static String getWorkloadPath() {
    String workloadConfPath =
        Constants.WORKLOAD_CONF_PREFIX + "." + WorkloadConf.get(Constants.WORKLOAD_NAME) + "."
            + Constants.WORKLOAD_PATH_SUFFIX;
    return WorkloadConf.get(workloadConfPath);
  }

  public static List<String> getSlavesHost() throws Exception {

    String slavesFilePath = WorkloadConf.get(Constants.SPARK_CLUSTER_SLAVE);
    BufferedReader br = new BufferedReader(new FileReader(new File(slavesFilePath)));
    List<String> slaves = new ArrayList<String>();
    String line;
    while ((line = br.readLine()) != null) {
      if (!line.startsWith("#")) {
        slaves.add(line);
      }
    }
    br.close();
    return slaves;
  }

  public static String getLogFileName() {
    return WorkloadConf.get(Constants.WORKLOAD_NAME) + Constants.DRIVER_LOG_SUFFIX;
  }

  public static String getWorkPath() {
    return WorkloadConf.get(Constants.WORKLOAD_OUTPUT_PATH);
  }

  // public static void print(String log) {
  // if ("true".equals(WorkloadConf.get(Constants.WORKLOAD_LOG))) {
  // System.out.println(log);
  // }
  // }

  /**
   * build properties for every execution, support runtime load configuration.
   * 
   * @param conf
   * @return
   * @throws IOException
   */
  public static Properties buildProperties(String conf) throws IOException {
    InputStream in = new BufferedInputStream(new FileInputStream(conf));
    Properties props = new Properties();
    props.load(in);
    in.close();
    return props;
  }

  public static List<String> getList(String str, String split) {
    String[] items = str.split(split);
    List<String> list = new ArrayList<String>();
    for (int i = 0; i < items.length; i++) {
      if (!items[i].trim().equals(""))
        list.add(items[i].trim());
    }

    return list;
  }

  // FIXME copy from XRunner.prepareDRunSh
  public static void prepareDes(String fileName) throws Exception {
    String str = WorkloadConf.get(Constants.WORKLOAD_RUNNER_COMMAND);
    String[] command = str.split(Constants.DATA_SPLIT);
    if (command.length == 1 && command[0].equals("")) {
      throw new Exception("NO COMMAND CONFIGURED");
    } else {
      Properties des = Util.buildProperties(fileName);
      for (int i = 0; i < command.length; i++) {
        // READ DES FILE AND WRAP INTO COMMANDDES OBJECT
        CommandDes cd = new CommandDes();
        List<GroupDes> groupDeses = new ArrayList<GroupDes>();
        List<ChartDes> chartDeses = new ArrayList<ChartDes>();
        cd.setStartSkip(Integer.parseInt(des.getProperty(command[i] + ".startSkip")));
        cd.setCommandName(command[i]);
        cd.setOwnFrequency(Integer.parseInt(des.getProperty(command[i] + ".frequency",
            WorkloadConf.get(Constants.WORKLOAD_RUNNER_FREQUENCY))));
        String groupContent = des.getProperty(command[i] + ".datagroup");
        if (groupContent.equals("null")) {
          GroupDes gd = new GroupDes();
          gd.setGroupName("null");
          // System.out.println(des.getProperty(command[i]+".datagroup.head"));
          List<String> headDes = Util.getList(des.getProperty(command[i] + ".datagroup.head"), ",");
          gd.setHeadDes(headDes);
          gd.setSplit(des.getProperty(command[i] + ".datagroup.split"));
          groupDeses.add(gd);
        } else {
          String[] group = groupContent.split("]");
          for (int k = 0; k < group.length; k++) {
            GroupDes gd = new GroupDes();
            groupInfoHandler(gd, group[k].substring(1));
            List<String> headDes =
                Util.getList(
                    des.getProperty(command[i] + ".datagroup." + gd.getGroupName() + ".head"),
                    Constants.DATA_SPLIT);
            gd.setHeadDes(headDes);
            gd.setSplit(des.getProperty(command[i] + ".datagroup." + gd.getGroupName() + ".split"));
            groupDeses.add(gd);
          }
        }
        String[] chartList = des.getProperty(command[i] + ".chart.list").split(",");
        for (int m = 0; m < chartList.length; m++) {
          ChartDes chd = new ChartDes();
          chd.setChartName(chartList[m]);
          String chartLine = des.getProperty(command[i] + ".chart." + chartList[m]);
          String chartInfo;
          if (chartLine == null) {
            throw new Exception("Cannot find chart Info for " + chartList[m]);
          }
          String[] chartItem = chartLine.split(";");
          chd.setYName(chartItem[0].split(":")[1]);
          if (chartItem.length == 4) {
            chartInfo = chartItem[3];
            String groupName = chartItem[1].split(":")[1];
            chd.setGroupName(groupName);
            String processInfo = chartItem[2];
            if (processInfo.startsWith(ChartProcessType.aggregate.toString())) {
              chd.setChartProcessType(ChartProcessType.aggregate);
              chd.setAggregateName(processInfo.split(" ")[1]);
            } else if (processInfo.startsWith(ChartProcessType.select.toString())) {
              chd.setChartProcessType(ChartProcessType.select);
              String[] selectInfo = processInfo.split(" ")[1].split(":");
              chd.setSelectName(selectInfo[0]);
              if (selectInfo.length > 1) {
                chd.setSelectValue(selectInfo[1]);
              }
            } else {
              throw new Exception("WRONG PROCESS TYPE: " + processInfo);
            }

          } else if (chartItem.length == 2)
            chartInfo = chartItem[1];
          else {
            throw new Exception("WRONG CHART DES: " + chartLine);
          }
          chartInfoHandler(chd, chartInfo);
          chartDeses.add(chd);
        }
        cd.setGroupDeses(groupDeses);
        cd.setChartDeses(chartDeses);
        WorkloadContext.put(command[i], cd);
      }
    }
  }

  private static void groupInfoHandler(GroupDes gd, String info) throws Exception {
    String[] groupItem = info.split(":");
    if (groupItem.length != 2) {
      System.out.println("Wrong Group Format");
      System.exit(1);
    }
    gd.setGroupName(groupItem[0].split(",")[0]);
    String regex = groupItem[0].split(",")[1];
    if (regex.equals(Regex.indexOf.toString()))
      gd.setRegex(Regex.indexOf);
    else if (regex.equals(Regex.startWith.toString()))
      gd.setRegex(Regex.startWith);
    else {
      throw new Exception("Cannot handle regex: " + regex);
    }
    gd.setRegexValue(groupItem[1]);
  }

  private static void chartInfoHandler(ChartDes chd, String chartInfo) {
    if (chartInfo.split(":")[0].equalsIgnoreCase(ChartType.line.toString()))
      chd.setChartType(ChartType.line);
    else if (chartInfo.split(":")[0].equalsIgnoreCase(ChartType.stack.toString()))
      chd.setChartType(ChartType.stack);
    chd.setColName(Util.getList(chartInfo.split(":")[1], ","));
  }
}
