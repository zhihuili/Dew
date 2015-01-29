package com.intel.sto.bigdata.app.asp;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import com.intel.sto.bigdata.app.asp.util.Util;

public class DrawChart {
  private List<String> listFilename = new ArrayList<String>();
  private List<String> listJob = new ArrayList<String>();
  private List<String> listGroup = new ArrayList<String>();

  private Map<String, ArrayList<String>> mapWorkload = new HashMap<String, ArrayList<String>>();
  private Map<String, ArrayList<String>> maplistAvg = new HashMap<String, ArrayList<String>>();
  private Map<String, String> mapFilename = new HashMap<String, String>();
  private Map<String, List<List<String>>> mapGroup = new HashMap<String, List<List<String>>>();
  private Map<String, TimeSeriesCollection> mapDataset =
      new HashMap<String, TimeSeriesCollection>();
  private TimeSeriesCollection avg_dataset = new TimeSeriesCollection();
  private Properties conf;

  // FIXME hehe
  private Map[] map = new Map[30000000];
  private List<Integer> listDate = new ArrayList<Integer>();
  private List<String> listFilePath = new ArrayList<String>();

  public void draw(Properties conf) throws Exception {

    String FILE_PATH = conf.getProperty("output");
    this.conf = conf;
    getFileList();
    getWorkloadList(FILE_PATH);
    getWorkloadMap();
    createGroupLineData();
    plotGroupChart();
    createAvgLineData();
    plotAvgChart();
    createHtml(mapGroup, maplistAvg);
    pushHtml(conf);

    System.out.println("listGroup: " + listGroup);
    System.out.println("listJob: " + listJob);
    System.out.println("listDate: " + listDate);
  }

  // create charts line data
  private void createGroupLineData() throws Exception {

    for (String group : listGroup) {
      List<List<String>> collection = new ArrayList<List<String>>();
      collection.clear();
      TimeSeriesCollection job_dataset = new TimeSeriesCollection();
      for (String job : mapWorkload.get(group)) {
        TimeSeries job_lineData = new TimeSeries(job);
        List<String> listOfTableData = new ArrayList<String>();
        String str_day;
        Date date;
        int day, day1, day2;
        double runtime1, runtime2, runtime_percent;

        job_lineData.clear();
        listOfTableData.clear();
        for (int j = 1; j < listDate.size(); j++) {
          day = listDate.get(j);
          // only show mmdd date
          str_day = Integer.toString(day);
          date = convertStringToDate(str_day.substring(4,8));
          try {
            day2 = listDate.get(j);
            day1 = listDate.get(j - 1);
            runtime2 = (Double) map[day2].get(group + "." + job);
            runtime1 = (Double) map[day1].get(group + "." + job);
            if (runtime1 != 0) {
              runtime_percent = (runtime2 - runtime1) / runtime1;
              // format output runtime percent
              DecimalFormat decimalFormat = new DecimalFormat("#.##");
              decimalFormat.setRoundingMode(RoundingMode.FLOOR);
              listOfTableData.add(decimalFormat.format(runtime_percent));
              job_lineData.add(new Day(date), runtime_percent);
            }
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        }
        job_dataset.addSeries(job_lineData);
        collection.add(listOfTableData);
      }
      mapGroup.put(group, collection);
      mapDataset.put(group, job_dataset);
      System.out.println("*******listCollection: " + collection);

    }
    System.out.println("*******MapGroup: " + mapGroup);
  }

  // draw group charts
  private void plotGroupChart() throws FileNotFoundException, IOException {
    for (String group : listGroup) {
      JFreeChart chart = ChartFactory.createTimeSeriesChart("Workloads Runtime", // Title
          "Date", // x-axis Label
          "Rumtime Percent (%)", // y-axis Label
          mapDataset.get(group), // Dataset
          true, // Show Legend
          true, // Use tooltips
          false // Configure chart to generate URLs?
          );
      /*
       * chart.setBackgroundPaint(Color.white); plot.setBackgroundPaint(Color.white);
       * plot.setDomainGridlinePaint(Color.gray); plot.setRangeGridlinePaint(Color.gray);
       */
      XYPlot plot = (XYPlot) chart.getPlot();
      DateAxis dateaxis = (DateAxis) plot.getDomainAxis();
      dateaxis.setDateFormatOverride(new SimpleDateFormat("MMdd"));
      int width = 500;
      int height = 350;
      BufferedImage workloadimage = chart.createBufferedImage(width, height);
      saveToFile(conf, workloadimage, group + "_workloads.png");
    }
  }

  // create average runtime linedata
  private void createAvgLineData() {

    for (String group : listGroup) {
      ArrayList<String> listAvg = new ArrayList<String>();
      TimeSeries avg_lineData = new TimeSeries(group);
      avg_lineData.clear();
      listAvg.clear();
      for (int j = 1; j < listDate.size(); j++) {
        double jobsum = 0, avg;
        int day, day1, day2;
        day = listDate.get(j);
        String str_day = Integer.toString(day);
        Date date = convertStringToDate(str_day.substring(4,8));
        try {
          day2 = listDate.get(j);
          day1 = listDate.get(j - 1);
          for (String job : mapWorkload.get(group)) {
            Double runtime2 = (Double) map[day2].get(group + "." + job);
            Double runtime1 = (Double) map[day1].get(group + "." + job);
            if (runtime1 != 0) {
              double runtime_percent = (runtime2 - runtime1) / runtime1;
              jobsum = jobsum + runtime_percent;
            }
          }
          // format output runtime percent
          DecimalFormat decimalFormat = new DecimalFormat("#.##");
          decimalFormat.setRoundingMode(RoundingMode.FLOOR);
          avg = jobsum / mapWorkload.get(group).size();
          listAvg.add(decimalFormat.format(avg));
          avg_lineData.add(new Day(date), avg);
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
      avg_dataset.addSeries(avg_lineData);
      maplistAvg.put(group, listAvg);
    }
    System.out.println("*******maplistAvg: " + maplistAvg);
  }

  // draw avg chart
  private void plotAvgChart() throws FileNotFoundException, IOException {
    JFreeChart chart2 = ChartFactory.createTimeSeriesChart("Avg Workload Runtime", // Title
        "Date", // x-axis Label
        "Avg Rumtime Percent (%)", // y-axis Label
        avg_dataset, // Dataset
        true, // Show Legend
        true, // Use tooltips
        false // Configure chart to generate URLs?
        );
    chart2.setBackgroundPaint(Color.white);
    XYPlot plot2 = (XYPlot) chart2.getPlot();
    plot2.setBackgroundPaint(Color.white);
    plot2.setDomainGridlinePaint(Color.gray);
    plot2.setRangeGridlinePaint(Color.gray);
    DateAxis dateaxis2 = (DateAxis) plot2.getDomainAxis();
    dateaxis2.setDateFormatOverride(new SimpleDateFormat("MMdd"));
    int width2 = 550;
    int height2 = 350;
    BufferedImage avgimage = chart2.createBufferedImage(width2, height2);
    saveToFile(conf, avgimage, "overall.png");
  }

  static void saveToFile(Properties conf, BufferedImage img, String imgname)
      throws FileNotFoundException, IOException {
    String IMAGE_PATH = conf.getProperty("imagedir");
    File outputfile = new File(IMAGE_PATH + "/" + imgname);
    ImageIO.write(img, "png", outputfile);
  }

  private void getWorkloadList(String FILE_PATH) throws FileNotFoundException,
      NumberFormatException, IOException {
    // HashSet hs = new HashSet();
    String line;

    int flag = 0;
    int file_day;
    for (String file : listFilePath) {
      flag++;
      // System.out.println("FILE_PATH: " + file);
      file_day = (Integer.parseInt(mapFilename.get(file)));
      map[file_day] = new HashMap<String, Double>();
      InputStream in = new FileInputStream(new File(file));
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));

      while ((line = reader.readLine()) != null) {
        StringTokenizer s = new StringTokenizer(line.toString(), "=");
        while (s.hasMoreTokens()) {
          String workload_name = s.nextToken();
          if (flag == 1) {
            listJob.add(workload_name);
            listGroup.add(workload_name.split("\\.")[0]);
          }
          deleteDuplicatedEle(listGroup);
          String runtime = s.nextToken();
          map[file_day].put(workload_name, Double.parseDouble(runtime));
        }
      }
    }
  }

  private void getWorkloadMap() {
    for (String group : listGroup) {
      ArrayList<String> listWorkload = new ArrayList<String>();
      for (String job : listJob) {
        if (job.split("\\.")[0].equals(group)) {
          listWorkload.add(job.split("\\.")[1]);
        }
        mapWorkload.put(group, listWorkload);
      }
    }
    System.out.println("into mapWorkload" + mapWorkload);
  }

  private void getFileList() throws IOException {
    String path = conf.getProperty("output");
    BufferedReader br = new BufferedReader(new FileReader(path + "/list"));
    try {
      String line;
      while ((line = br.readLine()) != null) {
        listFilename.add(line);
        listDate.add((Integer.parseInt(line)));
        // deleteDuplicatedEle(listDate);
        listFilePath.add(path + "/" + line);
        mapFilename.put((path + "/" + line), line);
      }
    } finally {
      if (br != null) {
        br.close();
      }
    }
    Collections.sort(listDate);
  }

  private Date convertStringToDate(String dateString) {
    Date date = null;
    DateFormat df = new SimpleDateFormat("MMdd");
    try {
      date = (Date) df.parse(dateString);
    } catch (Exception ex) {
      System.out.println(ex);
    }
    return date;
  }

  private void deleteDuplicatedEle(List list) {
    Set hs = new HashSet();
    hs.addAll(list);
    list.clear();
    list.addAll(hs);
  }

  private void createHtml(Map<String, List<List<String>>> mapGroup,
      Map<String, ArrayList<String>> maplistAvg) throws FileNotFoundException {
    VelocityEngine ve = new VelocityEngine();
    ve.init();
    /* next, get the Template */
    Template t = ve.getTemplate("Chart.vm");
    /* create a context and add data */
    VelocityContext context = new VelocityContext();
    context.put("tabledata", mapGroup);
    context.put("avgdata", maplistAvg);
    context.put("datelist", listDate);
    context.put("workload", mapWorkload);
    context.put("grouplist", listGroup);
    /* now render the template into a StringWriter */
    StringWriter writer = new StringWriter();
    String path =  conf.getProperty("imagedir")+ "../";
    PrintWriter out = new PrintWriter(path + "chart.html");
    t.merge(context, writer);
    out.println(writer.toString());
    out.close();
    System.out.println(writer.toString());
  }

  private void pushHtml(Properties conf) throws Exception {
    String path =  conf.getProperty("imagedir")+ "../";
    Util.execute("git add chart.html", null, path);
    Util.execute("git add image", null, path);
    Util.execute("git commit -m commit", null, path);
    Util.execute("git push origin gh-pages", null, path);
  }

}
