package com.intel.sto.bigdata.app.sparkperformance.chart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.intel.sto.bigdata.app.sparkperformance.Constants;
import com.intel.sto.bigdata.app.sparkperformance.Util;
import com.intel.sto.bigdata.app.sparkperformance.WorkloadConf;
import com.intel.sto.bigdata.app.sparkperformance.WorkloadContext;
import com.intel.sto.bigdata.app.sparkperformance.chart.preprocessor.XChartPreprocessor;
import com.intel.sto.bigdata.app.sparkperformance.des.ChartDes;
import com.intel.sto.bigdata.app.sparkperformance.des.ChartProcessType;
import com.intel.sto.bigdata.app.sparkperformance.des.ChartType;
import com.intel.sto.bigdata.app.sparkperformance.des.CommandDes;
import com.intel.sto.bigdata.app.sparkperformance.des.GroupDes;

public class XChart extends SparkChart implements SystemChart {
  JobChart jc;

  @Override
  public void createChart() throws Exception {
    String[] command =
        WorkloadConf.get(Constants.WORKLOAD_RUNNER_COMMAND).split(Constants.DATA_SPLIT);
    for (int i = 0; i < command.length; i++) {
      CommandDes cd = (CommandDes) WorkloadContext.get(command[i]);
      List<String> slaves = Util.getSlavesHost();
      for (String slave : slaves) {
        XChartPreprocessor xp = new XChartPreprocessor();
        xp.setCSVFolder(csvFolder);
        xp.setStartTime(jc.getStartTime());
        List<Map<String, List<List<String>>>> dataList = xp.getDataList(cd, slave);
        for (int m = 0; m < cd.getChartDes().size(); m++) {
          ChartDes chd = cd.getChartDes().get(m);
          ChartSource cs = getChartSource(chd, cd, dataList);
          cs.setRemainder(xp.getRemainder());
          if (chd.getChartType().equals(ChartType.line)) {
            outputGraph(command[i] + "_" + cs.chartName + "_" + slave, ChartUtil.lineChart(cs,slave),
                width, height);
          } else if (chd.getChartType().equals(ChartType.stack)) {
            outputGraph(command[i] + "_" + cs.chartName + "_" + slave, ChartUtil.stackChart(cs,slave),
                width, height);
          } else {
            System.out.println("WRONG CHART TYPE: " + chd.getChartType());
            System.exit(1);
          }
        }
      }
    }
  }

  private ChartSource getChartSource(ChartDes chd, CommandDes cd,
      List<Map<String, List<List<String>>>> dataList) throws Exception {
    ChartSource cs = new ChartSource();
    cs.setChartName(chd.getChartName());
    cs.setFreq(cd.getOwnFrequency());
    cs.setMarker(jc.getMarker());
    cs.setYAxisName(chd.getYName());
    List<String> dataHead = null;
    if (chd.getGroupName() == null) {
      dataHead = cd.getGroupDes().get(0).getHeadDes();
      List<List<Double>> data = getChartDataByRow(dataHead, dataList, chd);
      cs.setDataList(data);
      cs.setDataNameList(chd.getColName());

    } else {
      // FIND GROUPHEAD BY GROUPBYNAME
      List<GroupDes> groupDesList = cd.getGroupDes();
      if (chd.getChartProcessType().equals(ChartProcessType.select)) {
        for (int i = 0; i < groupDesList.size(); i++) {
          if (chd.getGroupName().equals(groupDesList.get(i).getGroupName())) {
            dataHead = groupDesList.get(i).getHeadDes();
            cs.setDataList(getChartDataByRow(dataHead, dataList, chd));
            cs.setDataNameList(chd.getColName());
          }
        }
      } else if (chd.getChartProcessType().equals(ChartProcessType.aggregate)) {
        for (int i = 0; i < groupDesList.size(); i++) {
          if (chd.getGroupName().equals(groupDesList.get(i).getGroupName())) {
            dataHead = groupDesList.get(i).getHeadDes();
            cs.setDataList(getChartDataByCol(dataHead, dataList, chd));
            List<String> nameList = new ArrayList<String>();
            for (int col = 0; col < dataHead.size(); col++) {
              if (chd.getAggregateName().equals(dataHead.get(col))) {
                List<List<String>> table = dataList.get(0).get(chd.getGroupName());
                for (int row = 0; row < table.size(); row++)
                  nameList.add(chd.getGroupName() + table.get(row).get(col));
              }
            }
            cs.setDataNameList(nameList);
          }
        }
      } else {
        throw new Exception("Cannot handle processType: " + chd.getChartProcessType());
      }
      if (dataHead == null) {
        throw new Exception("Cannot find group: " + chd.getGroupName());
      }
    }
    return cs;
  }

  private List<List<Double>> getChartDataByCol(List<String> dataHead,
      List<Map<String, List<List<String>>>> dataList, ChartDes chd) {
    List<List<Double>> chartData = new ArrayList<List<Double>>();
    for (int col = 0; col < dataHead.size(); col++) {
      if (chd.getColName().get(0).equals(dataHead.get(col))) {
        for (int num = 0; num < dataList.size(); num++) {
          List<Double> record = new ArrayList<Double>();
          for (int row = 0; row < dataList.get(num).get(chd.getGroupName()).size(); row++) {
            String element = dataList.get(num).get(chd.getGroupName()).get(row).get(col);
            record.add(Double.parseDouble(element));
          }
          chartData.add(record);
        }
        break;
      }
    }
    return chartData;
  }

  private List<List<Double>> getChartDataByRow(List<String> dataHead,
      List<Map<String, List<List<String>>>> dataList, ChartDes chd) throws Exception {
    List<List<Double>> chartData = new ArrayList<List<Double>>();
    List<String> chartHead = chd.getColName();
    String groupName = chd.getGroupName();
    String selectName = chd.getSelectName();
    String selectValue = chd.getSelectValue();
    // Search selectValue and locate the row number
    int rowIndex = 0;
    if (selectValue != null) {
      for (int col = 0; col < dataHead.size(); col++) {
        if (selectName.equals(dataHead.get(col))) {
          List<List<String>> table = dataList.get(0).get(groupName);
          for (int row = 0; row < table.size(); row++) {
            if (selectValue.equals(table.get(row).get(col)))
              rowIndex = row;
          }
        }
      }
    }
    for (int num = 0; num < dataList.size(); num++) {
      List<Double> record = new ArrayList<Double>();
      for (int k = 0; k < chartHead.size(); k++) {
        for (int col = 0; col < dataHead.size(); col++) {
          if (chartHead.get(k).equals(dataHead.get(col))) {
            double element =
                Double.parseDouble(dataList.get(num).get(chd.getGroupName() + "").get(rowIndex)
                    .get(col));
            record.add(element);
          }
        }
      }
      chartData.add(record);
    }
    return chartData;
  }

  @Override
  public void setJobChart(JobChart jc) {
    this.jc = jc;
    try {
      jc.setCsvFolder(csvFolder);
      jc.setJpgFolder(jpgFolder);
      jc.draw();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
