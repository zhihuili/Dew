package com.intel.sto.bigdata.app.sparkperformance.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisSpace;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.ui.Layer;
import org.jfree.chart.annotations.XYTextAnnotation;

import java.awt.Font;
import java.text.DecimalFormat;

import org.jfree.ui.TextAnchor;

import com.intel.sto.bigdata.app.sparkperformance.Constants;
import com.intel.sto.bigdata.app.sparkperformance.WorkloadConf;

public class ChartUtil {

  static Double xMax = 0.0;

  public static JFreeChart lineChart(ChartSource cs) {
    DefaultTableXYDataset dataset = createXYDataset(cs);
    JFreeChart chart =
        ChartFactory.createXYLineChart(cs.chartName.toUpperCase() + " THROUGHOUTPUT", cs.xAxisName,
            cs.yAxisName, dataset, PlotOrientation.VERTICAL, true, false, false);
    XYPlot plot = (XYPlot) chart.getPlot();
    chart.setBackgroundPaint(Color.white);
    plot.setBackgroundPaint(Color.white);
    plot.setDomainGridlinePaint(Color.gray);
    plot.setRangeGridlinePaint(Color.gray);
    XYItemRenderer renderer = plot.getRenderer();
    for (int i = 0; i < dataset.getSeriesCount(); i++) {
      renderer.setSeriesStroke(i, new BasicStroke(2f));// set the width of line
      renderer.setSeriesPaint(i,
          Color.getHSBColor((float) (i + 1) * (360 / dataset.getSeriesCount()) / 360, 1, 1));
    }
    ValueAxis rangeAxis = plot.getRangeAxis();
    ValueAxis domainAxis = plot.getDomainAxis();
    rangeAxis.setLowerBound(0);
    ((NumberAxis) rangeAxis).setNumberFormatOverride(new DecimalFormat("#.####E0"));
    if (WorkloadConf.get(Constants.WORKLOAD_CHART_XMAX) != null) {
      xMax = Double.parseDouble(WorkloadConf.get(Constants.WORKLOAD_CHART_XMAX));
    }
    domainAxis.setUpperBound(Math.max(xMax, cs.dataList.size() * cs.freq + 2));
    paintMarker(cs.marker, chart, "XYPLOT");
    addAnnoLabels(cs.marker, plot);
    return chart;
  }

  private static void paintMarker(double[][] marker, JFreeChart chart, String type) {
    // FIXME FIND A SET OF DIFFERENT COLORS
    AxisSpace as = new AxisSpace();
    as.setLeft(100);
    Paint[] paints = { Color.black, Color.darkGray, Color.gray };
    boolean flag;
    for (int k = 0; k < marker.length; k++)
      if (marker[k] != null) {
        for (int i = 0; i < marker[k].length; i++) {
          flag = false;
          if (k > 0) {
            for (int j = 0; j < marker[k - 1].length; j++)
              if (marker[k][i] == marker[k - 1][j]) {
                flag = true;
                break;
              }
          }
          if (flag == false) {
            Marker m =
                new ValueMarker(marker[k][i], paints[k % 3], new BasicStroke(
                    (float) ((marker.length - k) * 0.2)), null, null, 1.0f);
            if (type.equals("CATEGORYPLOT")) {
              chart.getCategoryPlot().addRangeMarker(m, Layer.FOREGROUND);
              ((CategoryPlot) chart.getPlot()).setFixedDomainAxisSpace(as);
            }

            else if (type.equals("XYPLOT")) {
              chart.getXYPlot().addDomainMarker(m, Layer.FOREGROUND);
              ((XYPlot) chart.getPlot()).setFixedRangeAxisSpace(as);
            }

          }
        }
      }
  }

  public static JFreeChart stackChart(ChartSource cs) {
    DefaultTableXYDataset dataset = createXYDataset(cs);
    JFreeChart chart =
        ChartFactory.createStackedXYAreaChart(cs.chartName.toUpperCase() + " UTILIZATION",
            cs.xAxisName, cs.yAxisName, dataset, PlotOrientation.VERTICAL, true, false, false);
    chart.setBackgroundPaint(Color.white);
    XYPlot plot = (XYPlot) chart.getPlot();
    plot.setForegroundAlpha(0.6f);
    plot.setBackgroundPaint(Color.white);
    final ValueAxis domainAxis = plot.getDomainAxis();
    domainAxis.setLowerMargin(0.0);
    domainAxis.setUpperMargin(0.0);
    final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
    // rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    // rangeAxis.setAutoRange(true);

    if (cs.yAxisName.equalsIgnoreCase("PERCENTAGE")) {
      // rangeAxis.setNumberFormatOverride(new DecimalFormat("#%"));
      // rangeAxis.setRange(0, 1.0);
      rangeAxis.setRange(0, 100);
    } else {
      rangeAxis.setNumberFormatOverride(new DecimalFormat("#.####E0"));
      double max = 0;
      for (int i = 0; i < dataset.getSeriesCount(); i++) {
        max += dataset.getYValue(i, cs.dataList.size() - 2);
      }
      rangeAxis.setRange(0, max);
    }
    if (WorkloadConf.get(Constants.WORKLOAD_CHART_XMAX) != null) {
      xMax = Double.parseDouble(WorkloadConf.get(Constants.WORKLOAD_CHART_XMAX));
    }
    domainAxis.setUpperBound(Math.max(xMax, cs.dataList.size() * cs.freq + 2));
    paintMarker(cs.marker, chart, "XYPLOT");
    addAnnoLabels(cs.marker, plot);
    return chart;
  }

  public static JFreeChart ganttaChart(ChartSource cs) {
    CategoryDataset dataset = createCategoryDataset(cs);
    final JFreeChart chart = ChartFactory.createStackedBarChart(cs.chartName, cs.xAxisName, // domain
                                                                                            // axis
                                                                                            // label
        cs.yAxisName, // range axis label
        dataset, // data
        PlotOrientation.HORIZONTAL, // the plot orientation
        false, // include legend
        false, // tooltips
        false // urls
        );
    final CategoryPlot plot = (CategoryPlot) chart.getPlot();
    final StackedBarRenderer renderer = (StackedBarRenderer) plot.getRenderer();
    renderer.setDrawBarOutline(false);
    renderer.setSeriesPaint(0, Color.white);
    renderer.setSeriesPaint(2, Color.white);
    plot.setBackgroundPaint(Color.white);
    plot.setRangeGridlinesVisible(false);
    plot.setDomainGridlinesVisible(false);
    NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
    rangeAxis.setLowerBound(0);
    rangeAxis.setUpperBound(Math.max(
        Double.parseDouble(WorkloadConf.get(Constants.WORKLOAD_CHART_XMAX)),
        dataset.getValue(2, dataset.getColumnCount() - 1).doubleValue()));
    if (cs.xAxisName.toLowerCase().equals(Constants.TASK_NAME)) {
      CategoryAxis domainAxis = plot.getDomainAxis();
      domainAxis.setTickLabelsVisible(false);
    }
    paintMarker(cs.marker, chart, "CATEGORYPLOT");
    return chart;
  }

  private static CategoryDataset createCategoryDataset(ChartSource cs) {
    int count = cs.dataList.get(0).size();
    String[] column = new String[count];
    for (int i = 0; i < count; i++) {
      column[i] = cs.xAxisName + " " + i;
    }
    String[] row = new String[3];
    row[0] = "Start";
    row[1] = "Duration";
    row[2] = "End";
    double[][] newData = new double[3][count];
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < count; j++) {
        newData[i][j] = cs.dataList.get(i).get(j) / 1000;
      }
    }
    return DatasetUtilities.createCategoryDataset(row, column, newData);
  }

  private static DefaultTableXYDataset createXYDataset(ChartSource cs) {
    DefaultTableXYDataset dataset = new DefaultTableXYDataset();
    for (int i = 0; i < cs.dataList.get(0).size(); i++) {
      XYSeries s = new XYSeries(cs.dataNameList.get(i), true, false);
      for (int j = 0; j < cs.dataList.size(); j++) {
        s.add(j * cs.freq + cs.remainder, cs.dataList.get(j).get(i));
      }
      dataset.addSeries(s);
    }
    return dataset;
  }

  public static void addAnnoLabels(double[][] marker, XYPlot plot) {
    if (marker[0] != null) {
      double[] XValue = marker[0];
      for (int i = 0; i < XValue.length; i++) {
        XYTextAnnotation label =
            new XYTextAnnotation(Constants.JOB_NAME + i, XValue[i], plot.getRangeAxis()
                .getUpperBound() * 0.75);
        // label.setRotationAnchor(TextAnchor.BOTTOM_CENTER);
        label.setRotationAngle(0.5 * Math.PI);
        label.setTextAnchor(TextAnchor.TOP_CENTER);
        label.setPaint(Color.black);
        label.setFont(new Font("TimesRoman", Font.BOLD, 10));
        plot.addAnnotation(label);
      }
    }
  }
}
