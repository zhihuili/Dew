package com.intel.sto.bigdata.app.sparkpowermeter;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.intel.sto.bigdata.app.appdiagnosis.DiagnosisContext;
import com.intel.sto.bigdata.app.appdiagnosis.DiagnosisResult;
import com.intel.sto.bigdata.app.appdiagnosis.Diagnostician;
import com.intel.sto.bigdata.app.appdiagnosis.util.FileUtil;
import com.intel.sto.bigdata.app.sparklogparser.DriverlogMain;
import com.intel.sto.bigdata.app.sparklogparser.model.App;
import com.intel.sto.bigdata.app.sparkperformance.Constants;
import com.intel.sto.bigdata.app.sparkperformance.Util;
import com.intel.sto.bigdata.app.sparkperformance.WorkloadConf;
import com.intel.sto.bigdata.app.sparkperformance.WorkloadContext;
import com.intel.sto.bigdata.app.sparkperformance.chart.ChartFactory;
import com.intel.sto.bigdata.app.sparkperformance.chart.preprocessor.XChartPreprocessor;
import com.intel.sto.bigdata.app.sparkperformance.des.CommandDes;
import com.intel.sto.bigdata.dew.app.AgentProxy;
import com.intel.sto.bigdata.dew.app.AppDes;
import com.intel.sto.bigdata.dew.app.AppProcessor;
import com.intel.sto.bigdata.dew.app.DoNothingAppProcessor;
import com.intel.sto.bigdata.dew.http.server.JettyStreamServer;
import com.intel.sto.bigdata.dew.service.sysmetrics.callback.DstatHttpCallback;
import com.intel.sto.bigdata.dew.service.sysmetrics.listener.SaveDstatListener;
import com.intel.sto.bigdata.dew.service.sysmetrics.message.DstatServiceRequest;
import com.intel.sto.bigdata.dew.service.sysmetrics.message.HttpDstatServiceRequest;
import com.intel.sto.bigdata.dew.utils.Host;

public class OfflineExecutor {

  public static void execute(String confPath, String desPath, long startTime, long endTime)
      throws Exception {
    String workPath = init(confPath, String.valueOf(startTime));
    InputStream is = new FileInputStream(WorkloadConf.get(Constants.SPARK_CLUSTER_SLAVE));
    Set<String> hosts = FileUtil.loadFile(is);
    analyzePerformance(workPath, desPath, hosts, startTime, endTime);

    // diagnose(workPath, hosts); Do you need it?
  }

  private static String init(String confPath, String appId) throws Exception {
    // prepare context
    Properties p = Util.buildProperties(confPath);
    WorkloadConf.set(p);
    String baseWorkPath = WorkloadConf.get(Constants.WORKLOAD_OUTPUT_PATH);
    String workPath = baseWorkPath + appId + "/";
    File workDir = new File(workPath);
    if (workDir.exists()) {
      System.err.println(workPath + " exists. delete it.");
      workDir.delete();
    }
    workDir.mkdir();
    WorkloadConf.set(Constants.WORKLOAD_OUTPUT_PATH, workPath);
    return workPath;
  }

  private static void analyzePerformance(String workPath, String desPath, Set<String> hosts,
      long startTime, long endTime) throws Exception {
    // create performance file.
    WorkloadContext.put(Constants.WORKLOAD_RUNTIME, startTime);
    String master = WorkloadConf.get("dew.master.url");
    createPerformanceFileByHttp(workPath, hosts, master, startTime, endTime);

    // create performance chart.
    Util.prepareDes(desPath);
    ChartFactory.getChart().createChart();
    System.out.println("Output analysis chart to " + workPath);
  }

  private static void diagnose(String workPath, Set<String> hosts) throws Exception {
    List<DiagnosisResult> diagnosisResultList =
        Diagnostician.diagnose(buildDiagnosisContext(workPath, hosts));
    File analysisFile = new File(workPath + "analysis.result");
    FileUtil.printAnalysisResult(diagnosisResultList, analysisFile);
  }

  public static void execute(String confPath, String desPath, String logPath) throws Exception {

    // parse spark driver log.
    App app = DriverlogMain.parseLogFile(logPath);

    String workPath = init(confPath, app.getAppId());

    // output parsed spark driver log to workPath.
    DriverlogMain.printApp(app, workPath);
    WorkloadConf.set(Constants.SPARK_CLUSTER_SLAVE, workPath + "executors.csv");

    analyzePerformance(workPath, desPath, app.getExecutors(), app.getStartTime(), app.getEndTime());

    diagnose(workPath, app.getExecutors());
  }

  private static DiagnosisContext buildDiagnosisContext(String workPath, Set<String> hosts)
      throws Exception {
    XChartPreprocessor xcp = new XChartPreprocessor();
    xcp.setCSVFolder(workPath);
    CommandDes cd = (CommandDes) WorkloadContext.get("dstat");
    Map<String, List<Map<String, List<List<String>>>>> map =
        new HashMap<String, List<Map<String, List<List<String>>>>>();
    for (String host : hosts) {
      map.put(host, xcp.getDataList(cd, host));

    }
    DiagnosisContext ac = new DiagnosisContext();
    ac.putPerformanceData(map);
    return ac;
  }

  private static void createPerformanceFile(String path, Set<String> hosts, String master,
      long startTime, long endTime) throws Exception {
    SaveDstatListener sdl = new SaveDstatListener(path, hosts);
    new AgentProxy(master, sdl, new AppDes(hosts)).requestService(new DstatServiceRequest(
        startTime, endTime));
    System.out.println("Load performance data file completely.");
  }

  private static void createPerformanceFileByHttp(String path, Set<String> hosts, String master,
      long startTime, long endTime) throws Exception {
    AppProcessor processor = new DoNothingAppProcessor();
    JettyStreamServer server = new JettyStreamServer(new DstatHttpCallback(path));
    String httpUrl = "http://" + Host.getName() + ":" + server.getPort();
    new AgentProxy(master, processor, new AppDes(hosts))
        .requestService(new HttpDstatServiceRequest(httpUrl, startTime, endTime));
    System.out.println("Load performance data file completely.");
  }
}
