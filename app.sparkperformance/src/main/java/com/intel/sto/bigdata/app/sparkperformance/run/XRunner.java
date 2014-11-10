package com.intel.sto.bigdata.app.sparkperformance.run;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.intel.sto.bigdata.app.sparkperformance.Constants;
import com.intel.sto.bigdata.app.sparkperformance.Util;
import com.intel.sto.bigdata.app.sparkperformance.WorkloadConf;
import com.intel.sto.bigdata.app.sparkperformance.WorkloadContext;
import com.intel.sto.bigdata.app.sparkperformance.des.ChartDes;
import com.intel.sto.bigdata.app.sparkperformance.des.ChartProcessType;
import com.intel.sto.bigdata.app.sparkperformance.des.ChartType;
import com.intel.sto.bigdata.app.sparkperformance.des.CommandDes;
import com.intel.sto.bigdata.app.sparkperformance.des.GroupDes;
import com.intel.sto.bigdata.app.sparkperformance.des.Regex;

public class XRunner extends Runner {

  /**
   * FIXME partly duplicate with Util.prepareDes 
   * build drun.sh read describe file to wordconfContext as command object
   * 
   * @throws Exception
   */
  private void prepareDRunSh() throws Exception {
    File shell = new File(WorkloadConf.get(Constants.WORKLOAD_WORKDIR) + "/drun_wl.sh");
    shell.setExecutable(true);
    BufferedWriter br = new BufferedWriter(new FileWriter(shell));
    String str = WorkloadConf.get(Constants.WORKLOAD_RUNNER_COMMAND);
    String[] command = str.split(Constants.DATA_SPLIT);
    if (command.length == 1 && command[0].equals("")) {
      br.close();
      throw new Exception("NO COMMAND CONFIGURED");
    } else {
      // System.out.println(WorkloadConf.get(Constants.WORKLOAD_WORKDIR) + "runner.des");
      Properties des =
          Util.buildProperties(WorkloadConf.get(Constants.WORKLOAD_WORKDIR) + "/runner.des");
      StringBuffer sb = new StringBuffer("");
      List<String> slaves = Util.getSlavesHost();
      sb.append("#!/bin/bash\n\n");
      sb.append("rm -f ./*.dat\n");
      // get slaves name
      sb.append("TARGET=(");
      for (int i = 0; i < slaves.size(); i++) {
        sb.append("\"" + slaves.get(i) + "\"");
        if (i < slaves.size() - 1)
          sb.append(" ");
      }
      sb.append(")\n\n");
      sb.append("for worker in ${TARGET[@]}\n" + "do\n");
      for (int i = 0; i < command.length; i++) {
        sb.append("ssh $worker -f \"rm -f ./" + command[i] + "_${worker}.dat &\"\n");//
        // FIXME Any better solution to output dstat
        if (command[i].equals(Constants.DSTAT_COMMAND)) {
          sb.append("ssh $worker -f "
              + "\""
              + des.getProperty(command[i] + ".command")
              + " "
              + command[i]
              + "_${worker}"
              + ".dat "
              + des.getProperty(command[i] + ".frequency",
                  WorkloadConf.get(Constants.WORKLOAD_RUNNER_FREQUENCY)) + " > "
              + "/dev/null &\"\n");
        } else {
          sb.append("ssh $worker -f "
              + "\""
              + des.getProperty(command[i] + ".command")
              + " "
              + des.getProperty(command[i] + ".frequency",
                  WorkloadConf.get(Constants.WORKLOAD_RUNNER_FREQUENCY)) + " > " + command[i]
              + "_${worker}" + ".dat &\"\n");
        }
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
            br.close();
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
              br.close();
              throw new Exception("WRONG PROCESS TYPE: " + processInfo);
            }

          } else if (chartItem.length == 2)
            chartInfo = chartItem[1];
          else {
            br.close();
            throw new Exception("WRONG CHART DES: " + chartLine);
          }
          chartInfoHandler(chd, chartInfo);
          chartDeses.add(chd);
        }
        cd.setGroupDeses(groupDeses);
        cd.setChartDeses(chartDeses);
        WorkloadContext.put(command[i], cd);
      }
      sb.append("done\n");

      String workloadConfRun =
          Constants.WORKLOAD_CONF_PREFIX + "." + WorkloadConf.get(Constants.WORKLOAD_NAME) + "."
              + Constants.WORKLOAD_RUN_SUFFIX;
      String logFile = Util.getLogFileName();
      sb.append("./" + WorkloadConf.get(workloadConfRun) + " > " + logFile + " 2>&1\n");
      sb.append("sleep " + WorkloadConf.get(Constants.WORKLOAD_RUNNER_SLEEPTIME) + "\n");
      // FIXME Should the kill command be in the des file
      sb.append("for worker in ${TARGET[@]}\n" + "do\n");
      for (int i = 0; i < command.length; i++) {
        sb.append("ssh $worker ps aux | grep -i " + "\"" + command[i] + "\""
            + " | grep -v \"grep\" | awk '{print $2}' | xargs ssh $worker kill -9\n");
      }
      sb.append("done\n");
      sb.append("for worker in ${TARGET[@]}\n" + "do\n");
      for (int i = 0; i < command.length; i++) {
        sb.append("scp ${worker}:./" + command[i] + "_${worker}.dat " + Util.getWorkloadPath()
            + "\n");
      }
      sb.append("done\n");
      br.write(sb.toString());
    }
    br.close();
  }

  private void groupInfoHandler(GroupDes gd, String info) throws Exception {
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

  private void chartInfoHandler(ChartDes chd, String chartInfo) {
    if (chartInfo.split(":")[0].equalsIgnoreCase(ChartType.line.toString()))
      chd.setChartType(ChartType.line);
    else if (chartInfo.split(":")[0].equalsIgnoreCase(ChartType.stack.toString()))
      chd.setChartType(ChartType.stack);
    chd.setColName(Util.getList(chartInfo.split(":")[1], ","));
  }

  /**
   * copy drun.sh to workload folder
   * 
   * @throws Exception
   */
  private void copyDRun2WorkloadPath() throws Exception {
    String drunCp =
        "/bin/cp " + WorkloadConf.get(Constants.WORKLOAD_WORKDIR) + "/drun_wl.sh "
            + Util.getWorkloadPath();
    Runtime runtime = Runtime.getRuntime();
    String[] cpCmd = { "/bin/sh", "-c", drunCp };
    System.out.println(drunCp);
    if (runtime.exec(cpCmd).waitFor() != 0) {
      throw new Exception("cp drun_wl.sh failed.");
    }
  }

  // public void testCommand(){
  // String[] command =
  // WorkloadConf.get(Constants.WORKLOAD_RUNNER_COMMAND).split(Constants.DATA_SPLIT);
  // for(int i = 0; i < command.length; i++){
  // CommandDes cd = (CommandDes) WorkloadContext.get(command[i]);
  // System.out.println("Command Name: "+cd.getCommandName());
  // System.out.println("startSkip: " + cd.getStartSkip());
  // List<GroupDes> gds = cd.getGroupDes();
  // List<ChartDes> cds = cd.getChartDes();
  // System.out.println("Group Deses");
  // for(int j = 0 ; j < gds.size(); j++){
  // System.out.println("Group Name: " + gds.get(j).getGroupName());
  // System.out.println("Regex: " + gds.get(j).getRegex());
  // System.out.println("Regex Value: " + gds.get(j).getRegexValue());
  // System.out.println("Group Split: " + gds.get(j).getSplit());
  // System.out.print("Group Head: " );
  // printList(gds.get(j).getHeadDes());
  // }
  // System.out.println("Chart Deses");
  // for(int j=0; j < cds.size(); j++){
  // System.out.println("ChartName: " + cds.get(j).getChartName());
  // System.out.println("YName: " + cds.get(j).getYName());
  // System.out.println("GroupName: " + cds.get(j).getGroupName());
  // System.out.println("ProcessType : " + cds.get(j).getChartProcessType());
  // System.out.println("Select Name: " + cds.get(j).getSelectName());
  // System.out.println("Select Value: " + cds.get(j).getSelectValue());
  // System.out.println("ChartType: " + cds.get(j).getChartType());
  // System.out.println("GroupColName: " );
  // System.out.println("AggregateName: " + cds.get(j).getAggregateName());
  // printList(cds.get(j).getColName());
  // // }
  // }
  // }
  //
  // public void printList(List<String> list){
  // for(int i=0; i<list.size(); i++){
  // System.out.print(list.get(i) + " ");
  // }
  // System.out.println();
  // }

  @Override
  public void run() throws Exception {
    prepareDRunSh();
    copyDRun2WorkloadPath();
    String runsh = Util.getWorkloadPath() + "drun_wl.sh";
    Runtime runtime = Runtime.getRuntime();
    String[] runCmd = { "/bin/sh", "-c", runsh };
    // System.out.println("RUN: " + runsh);
    System.out.println("START MONITORING AT: " + (long) System.currentTimeMillis());
    WorkloadContext.put(Constants.WORKLOAD_RUNTIME, (long) System.currentTimeMillis());
    if (runtime.exec(runCmd, null, new File(Util.getWorkloadPath())).waitFor() != 0) {
      throw new Exception("drun failed.");
    }
  }
}
