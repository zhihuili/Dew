package com.intel.sto.bigdata.app.asp;

import java.awt.Font;
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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.DefaultXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.XYSeries;

import com.intel.sto.bigdata.app.asp.util.Util;

public class DrawChart {
  private List<String> listFilename = new ArrayList<String>();
  private String[] fileArr={};
  private List<String> listJob = new ArrayList<String>();
  private List<String> listGroup = new ArrayList<String>();

  private Map<String, ArrayList<String>> mapWorkload = new HashMap<String, ArrayList<String>>();
  private Map<String, ArrayList<String>> maplistAvg = new HashMap<String, ArrayList<String>>();
  private Map<String, String> mapFilename = new HashMap<String, String>();
  private Map<String, List<List<String>>> mapGroup = new HashMap<String, List<List<String>>>();
  private Map<String, DefaultTableXYDataset> mapDataset = new HashMap<String, DefaultTableXYDataset>();
  private DefaultTableXYDataset avg_dataset = new DefaultTableXYDataset();
  private Properties conf;

  private Map[] map = new Map[20];
  private List<String> listFilePath = new ArrayList<String>();

  public void draw(Properties conf) throws Exception {
    
    String FILE_PATH = conf.getProperty("output");
    this.conf = conf;
    getFileList();
    getWorkloadList(FILE_PATH);
    getWorkloadMap();
    createAvgLineData();
    plotAvgChart();
    createGroupLineData();
    plotGroupChart();

    createHtml("./WEB/index.vm");
    createHtml("./WEB/result.vm");
    pushHtml(conf);

    System.out.println("listGroup: " + listGroup);
    System.out.println("listJob: " + listJob);
  }

  // create charts line data
  private void createGroupLineData() throws Exception {

    for (String group : listGroup) {
      List<List<String>> collection = new ArrayList<List<String>>();
      collection.clear();
      DefaultTableXYDataset job_dataset = new DefaultTableXYDataset();
      
      for (String job : mapWorkload.get(group)) {
    	  XYSeries job_lineData = new XYSeries(job,false,false);
 //       double[] job_lineData = {};
        double[] indexArr = {};
        List<String> listOfTableData = new ArrayList<String>();
        String current, former;
        double runtime1 = 0, runtime2 = 0, runtime_percent = 0;
        listOfTableData.clear();
        for (int j = 1; j < listFilename.size(); j++) {
          indexArr = addElement(indexArr,(j-1)*1.0);
          int i=j-1;
          try {
        	current = listFilename.get(j);
            former = listFilename.get(j-1);
            runtime2 = (Double) map[j].get(group + "." + job);
            runtime1 = (Double) map[j-1].get(group + "." + job);
            while (runtime1 == 0) {
              runtime1 = (Double) map[--i].get(group + "." + job);
            }
            if (runtime2 > 0){
              runtime_percent = 100.0 * (runtime2 - runtime1) / runtime1;
           // format output runtime percent
              DecimalFormat decimalFormat = new DecimalFormat("###.#");
              decimalFormat.setRoundingMode(RoundingMode.FLOOR);
              listOfTableData.add(decimalFormat.format(runtime_percent));
              job_lineData.add((j-1)*1.0, runtime_percent);
            }
            else  {
                listOfTableData.add(null);
            	job_lineData.add((j-1)*1.0, null);
            }
          } catch (NullPointerException ex) {
        	  System.out.println(ex);
          }
//          job_lineData = addElement(job_lineData,runtime_percent);
        }
        job_dataset.addSeries(job_lineData);
 //       job_dataset.addSeries(job, new double[][]{indexArr,job_lineData});
        collection.add(listOfTableData);

      }
      mapGroup.put(group, collection);
      mapDataset.put(group, job_dataset);
      System.out.println("*******listcollection: " + collection);
    }
    System.out.println("*******MapGroup: " + mapGroup);
 
  }
    
  private String[] append(String[] i, String e){
	  i = Arrays.copyOf(i,i.length+1);
	  i[i.length-1]=e;
	  return i;
  }
  
  private double[] addElement(double[] a, double e){
	  a = Arrays.copyOf(a,a.length+1);
	  a[a.length-1]=e;
	  return a;
  }
  // draw group charts
  private void plotGroupChart() throws FileNotFoundException, IOException {
    for (String group : listGroup) {
      ValueAxis yAxis = new NumberAxis();
      ValueAxis xAxis = new SymbolAxis("Symbol", fileArr);
      DefaultXYItemRenderer renderer = new DefaultXYItemRenderer();
      XYPlot plot = new XYPlot(mapDataset.get(group), xAxis, yAxis, renderer);
      JFreeChart chart = new JFreeChart("Workloads Rumtime Performance", new Font("Dialog",Font.PLAIN,15),plot,true);
      BufferedImage workloadimage = chart.createBufferedImage(500, 350);
      saveToFile(conf, workloadimage, group + "_workloads.png");
    }
  }

  // create average runtime linedata
  private void createAvgLineData() {
	int flag = 0;
    for (String group : listGroup) {
      flag++;
      ArrayList<String> listAvg = new ArrayList<String>();
      XYSeries avg_lineData = new XYSeries(group,false, false);
 //     double[]  avg_lineData = {};
      double[] indexArr = {};
      
      for (int j = 1; j < listFilename.size(); j++) {
    	int i = j-1;
    	if (flag==1){
    	  fileArr = append(fileArr, listFilename.get(j));
    	}
        indexArr = addElement(indexArr,(j-1)*1.0);
        double jobsum = 0, runtime_percent, avg;
        String current, former;
        int exception_count=0;
        current = listFilename.get(j);
        former = listFilename.get(j - 1);
        for (String job : mapWorkload.get(group)) {
            try {
            Double runtime2 = (Double) map[j].get(group + "." + job);
            Double runtime1 = (Double) map[j-1].get(group + "." + job);
            while (runtime1 == 0) {
              runtime1 = (Double) map[--i].get(group + "." + job);
            }
            runtime_percent = 100 * (runtime2 - runtime1) / runtime1;
            } catch (NullPointerException ex) {
                map[j].put(group + "." + job, 0.00);
                runtime_percent = 0;
                //count no value workloads num
                exception_count++;
                System.out.println(current + group + "." + job + " have no value.");
            }
            jobsum = jobsum + runtime_percent;
        }
        // format output runtime percent
        DecimalFormat decimalFormat = new DecimalFormat("###.#");
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);
        avg = jobsum / (mapWorkload.get(group).size()-exception_count);
        listAvg.add(decimalFormat.format(avg));
        avg_lineData.add((j-1)*1.0, avg);
 //       avg_lineData = addElement( avg_lineData, avg );
      }
      avg_dataset.addSeries(avg_lineData);
//      avg_dataset.addSeries(group, new double[][]{indexArr,avg_lineData});
      maplistAvg.put(group, listAvg);
    }
    System.out.println("maplistAvg: " + maplistAvg);
    System.out.println("map: " + map[1]);

  }

  // draw avg chart
  private void plotAvgChart() throws FileNotFoundException, IOException {
    ValueAxis yAxis = new NumberAxis();
    ValueAxis xAxis = new SymbolAxis("Symbol", fileArr);
    XYItemRenderer renderer = new XYLineAndShapeRenderer();
    XYPlot plot = new XYPlot(avg_dataset, xAxis, yAxis, renderer);
    JFreeChart chart = new JFreeChart("Avg Workload Rumtime", new Font("Dialog",Font.PLAIN,15),plot,true);   
    BufferedImage avgimage = chart.createBufferedImage(250, 200);
    saveToFile(conf, avgimage, "overall.png");
  }

  static void saveToFile(Properties conf, BufferedImage img, String imgname)
      throws FileNotFoundException, IOException {
    String IMAGE_PATH = conf.getProperty("imagedir");
    File theDir = new File(IMAGE_PATH);
    if (!theDir.exists()){
    	System.out.println("creating image dir:" + IMAGE_PATH);
    	new File(IMAGE_PATH).mkdirs();
    }
    File outputfile = new File(IMAGE_PATH + imgname);
    ImageIO.write(img, "png", outputfile);
  }

  private void getWorkloadList(String FILE_PATH) throws FileNotFoundException,
      NumberFormatException, IOException {
    // HashSet hs = new HashSet();
    String line;

    int flag = 0, index = 0;
    for (String file : listFilePath) {
      flag++;
      map[index] = new HashMap<String, Double>();
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
          map[index].put(workload_name, Double.parseDouble(runtime));
        }
      }
      index++;
      reader.close();
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
    System.out.println("mapWorkload" + mapWorkload);
  }

  private void getFileList() throws IOException {
    String path = conf.getProperty("output");
    BufferedReader br = new BufferedReader(new FileReader(path + "/list"));
    try {
      String line;
      while ((line = br.readLine()) != null) {
        listFilename.add(line);
        listFilePath.add(path + "/" + line);
        mapFilename.put((path + "/" + line), line);
      }
    } finally {
      if (br != null) {
        br.close();
      }
    }
    System.out.println("listFilename:" + listFilename);
  }

  private void deleteDuplicatedEle(List list) {
    Set hs = new HashSet();
    hs.addAll(list);
    list.clear();
    list.addAll(hs);
  }

  private void createHtml(String vm) throws FileNotFoundException {
    VelocityEngine ve = new VelocityEngine();
    String name = vm.split("/")[2].split("\\.")[0];
    System.out.println("name: " + name);
    ve.init();
    /* next, get the Template */
    Template t = ve.getTemplate(vm);
    /* create a context and add data */
    List<String> listshowFile = Arrays.asList(fileArr);
    VelocityContext context = new VelocityContext();
    context.put("tabledata", mapGroup);
    context.put("avgdata", maplistAvg);
    context.put("filelist", listshowFile);
    context.put("workload", mapWorkload);
    context.put("grouplist", listGroup);
    /* now render the template into a StringWriter */
    StringWriter writer = new StringWriter();
    String path =  conf.getProperty("imagedir")+ "../";
    PrintWriter out = new PrintWriter(path + name +".html");
    System.out.println("+++++outputdir: "+ path + name +".html");
    t.merge(context, writer);
    out.println(writer.toString());
    out.close();
    System.out.println(writer.toString());
  }

  private void pushHtml(Properties conf) throws Exception {
    String path =  conf.getProperty("imagedir")+ "../";
    Util.execute("git add index.html", null, path);
    Util.execute("git add result.html", null, path);
    Util.execute("git add about.html", null, path);
    Util.execute("git add css", null, path);
    Util.execute("git add image", null, path);
    Util.execute("git commit -m commit", null, path);
//    Util.execute("git push origin gh-pages", null, path);
    Util.execute("git push https://yanqinghuang:1991hyq*@github.com/yanqinghuang/Asp.git",null ,path);
  }

}
