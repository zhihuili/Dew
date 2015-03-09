package com.intel.sto.bigdata.app.asp.chart;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;

public class Chart {

  public void draw(Data data) throws Exception {
    plotAvgChart(data);
    plotDetailChart(data);
  }

  private void plotDetailChart(Data data) throws Exception {
    for (Entry<String, List<List<Float>>> entry : data.getGroupDetail().entrySet()) {
      String groupName = entry.getKey();
      List<String> lines = data.getGroupLines().get(groupName);
      DefaultTableXYDataset dataSet = new DefaultTableXYDataset();
      List<List<Float>> detailListList = entry.getValue();
      for (int i = 0; i < detailListList.size(); i++) {
        XYSeries lineData = new XYSeries(lines.get(i), false, false);
        List<Float> detailList = detailListList.get(i);
        for (int j = 0; j < detailList.size(); j++) {
          lineData.add(j, detailList.get(j));
        }
        dataSet.addSeries(lineData);
      }
      plotChart(groupName + "_workloads", "Performance " + groupName, dataSet, data);
    }
  }

  private void plotAvgChart(Data data) throws Exception {
    DefaultTableXYDataset dataSet = new DefaultTableXYDataset();
    for (Entry<String, List<Float>> entry : data.getNewGroupAvg().entrySet()) {
      XYSeries lineData = new XYSeries(entry.getKey(), false, false);
      List<Float> list = entry.getValue();
      for (int i = 0; i < list.size(); i++) {
        lineData.add(i, list.get(i));
      }
      dataSet.addSeries(lineData);
    }
    plotChart("overall", "Performance Overall", dataSet, data);
  }

  private void plotChart(String name, String title, XYDataset dataSet, Data data) throws Exception {
    ValueAxis yAxis = new NumberAxis();
    ValueAxis xAxis =
        new SymbolAxis("", data.getFileList().subList(1, data.getFileList().size())
            .toArray(new String[data.getFileList().size() - 1]));
    XYItemRenderer renderer = new XYLineAndShapeRenderer();
    XYPlot plot = new XYPlot(dataSet, xAxis, yAxis, renderer);
    JFreeChart chart = new JFreeChart(title, new Font("Dialog", Font.PLAIN, 15), plot, true);
    BufferedImage avgImage = chart.createBufferedImage(500, 200);
    saveToFile(avgImage, name + ".png", data.getImgDir());
  }

  public void saveToFile(BufferedImage img, String imgname, String imagePath) throws Exception {
    File theDir = new File(imagePath);
    if (!theDir.exists()) {
      new File(imagePath).mkdirs();
    }
    File outputfile = new File(imagePath, imgname);
    ImageIO.write(img, "png", outputfile);
  }

}
