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
import java.util.Map;
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
  private static ArrayList<String> listFilename = new ArrayList<String>();
  private static ArrayList<String> listWorkload = new ArrayList<String>();
  private static ArrayList<String> listJob = new ArrayList<String>();
  private static ArrayList<String> listGroup = new ArrayList<String>();

  private static Map<String, String> mapFilename = new HashMap<String, String>();
  private static Map<String, ArrayList<ArrayList<String>>> mapGroup = new HashMap<String, ArrayList<ArrayList<String>>>();
  private static Map<String, TimeSeriesCollection> mapDataset = new HashMap<String, TimeSeriesCollection>();
 
  private static Map[] map = new Map[30000000];
  private static ArrayList<Integer> listDate = new ArrayList<Integer>();
  private static ArrayList<String> listFilePath = new ArrayList<String>();
  
  public static void draw(Map<String, String> conf) throws Exception {
 
    Map<String,ArrayList<String>> maplistAvg = new HashMap<String,ArrayList<String>>();
    
    String FILE_PATH = conf.get("output");
   
    TimeSeriesCollection avg_dataset = new TimeSeriesCollection();

    getFileList(conf);
    getWorkloadMapList(FILE_PATH);
    
    System.out.println("listGroup: " + listGroup);
    System.out.println("listWorkload: " + listWorkload);
    System.out.println("listDate: " + listDate);
  
    //create charts line data
for (String group : listGroup){
	ArrayList<ArrayList<String>> collection = new ArrayList<ArrayList<String>>();
	collection.clear();
	TimeSeriesCollection job_dataset = new TimeSeriesCollection();
    for (String job : listWorkload) {
      TimeSeries job_lineData = new TimeSeries(job);
      ArrayList<String> listOfTableData = new ArrayList<String>();
      String str_day;
      Date date;
      int day, day1, day2;
      double runtime1, runtime2, runtime_percent;
          
      job_lineData.clear();
      listOfTableData.clear();
      for (int j = 1; j < listDate.size(); j++) {
        day = listDate.get(j);
        // only show mmdd date
        day = day % 10000;
        str_day = Integer.toString(day);
        date = convertStringToDate(str_day);
        try {
          day2 = listDate.get(j);
          day1 = listDate.get(j - 1);
          runtime2 = (Double) map[day2].get(group+"."+job);
          runtime1 = (Double) map[day1].get(group+"."+job);
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
//draw group charts
for (String group : listGroup){
    JFreeChart chart = ChartFactory.createTimeSeriesChart("Workloads Runtime", // Title
        "Date", // x-axis Label
        "Rumtime Percent (%)", // y-axis Label
        mapDataset.get(group), // Dataset
        true, // Show Legend
        true, // Use tooltips
        false // Configure chart to generate URLs?
        );
    chart.setBackgroundPaint(Color.white);
    XYPlot plot = (XYPlot) chart.getPlot();
    plot.setBackgroundPaint(Color.white);
    plot.setDomainGridlinePaint(Color.gray);
    plot.setRangeGridlinePaint(Color.gray);
    DateAxis dateaxis = (DateAxis) plot.getDomainAxis();
    dateaxis.setDateFormatOverride(new SimpleDateFormat("MMdd"));
    int width = 500;
    int height = 350;
    BufferedImage workloadimage = chart.createBufferedImage(width, height);
    saveToFile(conf, workloadimage, group+"_workloads.png");
    ;
}
    // create average runtime linedata   
  for(String group : listGroup){
	  ArrayList<String> listAvg = new ArrayList<String>();
      TimeSeries avg_lineData = new TimeSeries(group); 
      avg_lineData.clear();
      listAvg.clear();     
      for (int j = 1; j < listDate.size(); j++) {
       	double jobsum = 0, avg;
        int day, day1, day2;
        day = listDate.get(j);
        day = day % 10000;       /* only show mmdd date  */
        String str_day = Integer.toString(day);
        Date date = convertStringToDate(str_day);
        try {
        day2 = listDate.get(j);
        day1 = listDate.get(j - 1);
        for (String job : listWorkload) {
          Double runtime2 = (Double) map[day2].get(group+"."+job);
          Double runtime1 = (Double) map[day1].get(group+"."+job);
          if (runtime1 != 0) {
            double runtime_percent = (runtime2 - runtime1) / runtime1;
            jobsum = jobsum + runtime_percent;
          }
         }
        // format output runtime percent
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);
        avg = jobsum / listWorkload.size();
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
  //draw avg chart
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

    createHtml(mapGroup, maplistAvg);
    pushHtml();
  }

  static void saveToFile(Map<String, String> conf, BufferedImage img, String imgname)
      throws FileNotFoundException, IOException {
    String IMAGE_PATH = conf.get("imagedir");
    File outputfile = new File(IMAGE_PATH + "/" + imgname);
    ImageIO.write(img, "png", outputfile);
  }

  static void getWorkloadMapList(String FILE_PATH) throws FileNotFoundException,
      NumberFormatException, IOException {
    HashSet hs = new HashSet();
    String line;

    int flag = 0;
    int file_day;
    for (String file : listFilePath) {
      flag++;
      System.out.println("FILE_PATH: " + file);
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
            listWorkload.add(workload_name.split("\\.")[1]);
            listGroup.add(workload_name.split("\\.")[0]);
            deleteDuplicatedEle(listWorkload);
            deleteDuplicatedEle(listGroup);
          }
          String runtime = s.nextToken();
          map[file_day].put(workload_name, Double.parseDouble(runtime));
        }
      }
      System.out.println(map[file_day]);
      System.out.println(listJob);

    }
  }

  static void getFileList(Map<String, String> conf) throws IOException {
    String path = conf.get("output");
    BufferedReader br = new BufferedReader(new FileReader(path + "/list"));
    try {
      String line;
      while ((line = br.readLine()) != null) {
        listFilename.add(line);
        listDate.add((Integer.parseInt(line)));
        deleteDuplicatedEle(listDate);
        listFilePath.add(path + "/" + line);
        mapFilename.put((path + "/" + line), line);
      }
    } finally {
      if (br != null) {
        br.close();
      }
    }
    deleteDuplicatedEle(listFilename);
    deleteDuplicatedEle(listJob);

    deleteDuplicatedEle(listFilePath);
    Collections.sort(listDate);
  }

  public static Date convertStringToDate(String dateString) {
    Date date = null;
    DateFormat df = new SimpleDateFormat("MMdd");
    try {
      date = (Date) df.parse(dateString);
    } catch (Exception ex) {
      System.out.println(ex);
    }
    return date;
  }

  static void deleteDuplicatedEle(ArrayList list) {
    HashSet hs = new HashSet();
    hs.addAll(list);
    list.clear();
    list.addAll(hs);
  }

  static void createHtml(Map<String, ArrayList<ArrayList<String>>> mapGroup, Map<String,ArrayList<String>> maplistAvg)
      throws FileNotFoundException {
    VelocityEngine ve = new VelocityEngine();
    ve.init();
    /* next, get the Template */
    Template t = ve.getTemplate("Chart.vm");
    /* create a context and add data */
    VelocityContext context = new VelocityContext();
    context.put("tabledata", mapGroup);
    context.put("avgdata", maplistAvg);
    context.put("datelist", listDate);
    context.put("workloadlist", listWorkload);
    context.put("grouplist", listGroup);
    /* now render the template into a StringWriter */
    StringWriter writer = new StringWriter();
    PrintWriter out = new PrintWriter("chart.html");
    t.merge(context, writer);
    out.println(writer.toString());
    out.close();
    System.out.println(writer.toString());
  }

  public static void pushHtml() throws Exception {
	String commit = null;
	commit = commit + "0" ;
    Util.execute("git add chart.html", null, null);
    Util.execute("git add image", null, null);
    Util.execute("git commit -m commit", null, null);
    Util.execute("git push origin gh-pages", null, null);
  }

}
