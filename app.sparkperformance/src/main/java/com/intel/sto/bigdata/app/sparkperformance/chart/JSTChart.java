package com.intel.sto.bigdata.app.sparkperformance.chart;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.jfree.chart.JFreeChart;

import com.intel.sto.bigdata.app.sparkperformance.Constants;
import com.intel.sto.bigdata.app.sparkperformance.Util;
import com.intel.sto.bigdata.app.sparkperformance.WorkloadConf;

public class JSTChart extends SparkChart implements JobChart {
  /***
   * generate job,stage,task graph and fill the marker array
   */
  List<List<Double>> jobData;
  List<List<Double>> stageData;
  List<List<Double>> taskData;
  // double[] jobMarker = null;
  // double[] stageMarker = null;
  // FIXME Marker CAN BE CONFIGURED
  double[][] marker = new double[5][];
  static Long startTime = 0l;

  public double[] findTimeEnd(List<List<Double>> list) {
    int n = list.get(0).size();
    double[] timeend = new double[n];
    for (int i = 0; i < n; i++) {
      StringTokenizer st = new StringTokenizer(list.get(2).get(i).toString(), ".");
      String end = st.nextToken();
      timeend[i] = (Long.parseLong(end) / 1000);
    }
    return timeend;
  }

  public List<List<Double>> loadJSTCSV(String name, int sortCriteria) throws Exception {
    sortCSVFile(name, sortCriteria);
    FileReader fr = new FileReader(csvFolder + name + Constants.CSV_SUFFIX);
    BufferedReader br = new BufferedReader(fr);
    List<List<Double>> tempData = new ArrayList<List<Double>>(3);
    List<Double> startList = new ArrayList<Double>();
    List<Double> durationList = new ArrayList<Double>();
    List<Double> endList = new ArrayList<Double>();
    String line;
    StringTokenizer st;
    Double start, end;
    while (true) {
      line = br.readLine();
      if (line == null)
        break;
      st = new StringTokenizer(line, Constants.DATA_SPLIT);
      st.nextToken();
      if (name.compareTo(Constants.JOB_NAME) == 0)
        ;
      else if (name.compareTo(Constants.STAGE_NAME) == 0)
        st.nextToken();
      else if (name.compareTo(Constants.TASK_NAME) == 0) {
        st.nextToken();
        st.nextToken();
      }
      StringTokenizer st1 = new StringTokenizer(st.nextToken(), ".");
      start = Double.parseDouble(st1.nextToken().trim());
      st1 = new StringTokenizer(st.nextToken(), ".");
      end = Double.parseDouble(st1.nextToken().trim());
      startList.add(start - startTime);
      durationList.add(end - start);
      endList.add(end - startTime);
    }
    br.close();
    tempData.add(startList);
    tempData.add(durationList);
    tempData.add(endList);
    return tempData;
  }

  public long setStartTime() throws Exception {
    sortCSVFile(Constants.JOB_NAME, 2);
    FileReader fr = new FileReader(csvFolder + Constants.JOB_NAME + Constants.CSV_SUFFIX);
    BufferedReader br = new BufferedReader(fr);
    String line = br.readLine();
    while (line == "") {
      line = br.readLine();
      if (line == null) {
        br.close();
        throw new Exception("nothing in job.csv.");
      }
    }
    StringTokenizer st = new StringTokenizer(line, Constants.DATA_SPLIT);
    st.nextToken();
    String start = st.nextToken();
    br.close();
    return Long.parseLong(start.trim());
  }

  public ChartSource getChartSource(List<List<Double>> data, int freq, String chartName,
      String yAxisName, String xAxisName, double[][] marker) {
    ChartSource cs = new ChartSource();
    cs.setChartName(chartName);
    cs.setDataList(data);
    cs.setYAxisName(yAxisName);
    cs.setXAxisName(xAxisName);
    cs.setMarker(marker);
    return cs;
  }

  @Override
  public void createChart() throws Exception {

  }

  @Override
  public Long getStartTime() {
    return startTime;
  }

  @Override
  public void draw() {
    for (int i = 0; i < marker.length; i++)
      marker[i] = null;
    if (!WorkloadConf.get(Constants.WORKLOAD_STEP_PARSER).equals("emptyParser")) {
      try {
        startTime = setStartTime();
        jobData = loadJSTCSV(Constants.JOB_NAME, 2);
        stageData = loadJSTCSV(Constants.STAGE_NAME, 3);
        taskData = loadJSTCSV(Constants.TASK_NAME, 4);
        marker[0] = findTimeEnd(jobData);
        marker[1] = findTimeEnd(stageData);
        JFreeChart chart =
            ChartUtil.ganttaChart(getChartSource(jobData, freq, Constants.JOB_NAME.toUpperCase()
                + "-TIME", "time(s)", Constants.JOB_NAME.toUpperCase(), marker));
        outputGraph(Constants.JOB_NAME, chart, width, height);
        chart =
            ChartUtil.ganttaChart(getChartSource(stageData, freq,
                Constants.STAGE_NAME.toUpperCase() + "-TIME", "time(s)",
                Constants.STAGE_NAME.toUpperCase(), marker));
        outputGraph(Constants.STAGE_NAME, chart, width, height);
        chart =
            ChartUtil.ganttaChart(getChartSource(taskData, freq, Constants.TASK_NAME.toUpperCase()
                + "-TIME", "time(s)", Constants.TASK_NAME.toUpperCase(), marker));
        outputGraph(Constants.TASK_NAME, chart, width, height);
      } catch (Exception e) {
        System.err.print(e.getMessage());
      }
    }
  }

  /**
   * sort filename.csv according to the column indexed at sortCriteria
   * 
   */
  public void sortCSVFile(String filename, int sortCriteria) throws Exception {
    String taskSort =
        "sort -t \",\" -g -k " + Integer.toString(sortCriteria) + " " + csvFolder + filename
            + Constants.CSV_SUFFIX + " > " + csvFolder + "sorttmp" + Constants.CSV_SUFFIX;
    Util.runCmd(taskSort);
    String taskCp =
        "/bin/cp " + csvFolder + "sorttmp" + Constants.CSV_SUFFIX + " " + csvFolder + filename
            + Constants.CSV_SUFFIX;
    Util.runCmd(taskCp);
  }

  @Override
  // 0-jobmarker;1-stagemarker
  public double[][] getMarker() {
    return marker;
  }
}
