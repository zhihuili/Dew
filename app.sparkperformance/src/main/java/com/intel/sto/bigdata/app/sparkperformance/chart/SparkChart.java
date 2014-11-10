package com.intel.sto.bigdata.app.sparkperformance.chart;

import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import com.intel.sto.bigdata.app.sparkperformance.Constants;
import com.intel.sto.bigdata.app.sparkperformance.WorkloadConf;

/**
 * Read CSV files, then output JPG files.
 * 
 */
public abstract class SparkChart {
  String csvFolder;
  String jpgFolder;
  int width = Integer.parseInt(WorkloadConf.get(Constants.WORKLOAD_CHART_WIDTH));
  int height = Integer.parseInt(WorkloadConf.get(Constants.WORKLOAD_CHART_HEIGHT));
  int freq = Integer.parseInt(WorkloadConf.get(Constants.WORKLOAD_RUNNER_FREQUENCY));

  public abstract void createChart() throws Exception;

  public void setCsvFolder(String csvFolder) {
    this.csvFolder = csvFolder;
  }

  public void setJpgFolder(String jpgFolder) {
    this.jpgFolder = jpgFolder;
  }

  public void outputGraph(String name, JFreeChart chart, int width, int height) throws IOException {
    if (Constants.GRAPH_SUFFIX.equals(".png")) {
      ChartUtilities.saveChartAsPNG(new File(jpgFolder + name + Constants.GRAPH_SUFFIX), chart,
          width, height);
    } else {
      ChartUtilities.saveChartAsJPEG(new File(jpgFolder + name + Constants.GRAPH_SUFFIX), chart,
          width, height);

    }
  }
}
