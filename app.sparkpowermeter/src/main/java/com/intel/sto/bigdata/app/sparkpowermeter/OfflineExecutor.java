package com.intel.sto.bigdata.app.sparkpowermeter;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import com.intel.sto.bigdata.dew.utils.Files;
import com.intel.sto.bigdata.dew.utils.Host;

public class OfflineExecutor {

  public static void execute(String confPath, String desPath, long startTime, long endTime)
      throws Exception {
    String workPath = init(confPath, String.valueOf(startTime), new HashMap<String, String>());
    Set<String> hosts = loadSlaveSet();
    analyzePerformance(workPath, desPath, hosts, startTime, endTime);

    diagnose(workPath, hosts); // Do you need it?
  }

  private static Set<String> loadSlaveSet() throws Exception {
    String slaveFilePath = WorkloadConf.get(Constants.SPARK_CLUSTER_SLAVE);
    if (slaveFilePath == null) {
      String dewHome = System.getenv("DEW_HOME");
      File slaveFile = new File(dewHome + "/conf", "slaves");
      if (!slaveFile.exists()) {
        throw new Exception("Cannot find slave file.");
      }
      slaveFilePath = slaveFile.getAbsolutePath();
    }
    InputStream is = new FileInputStream(slaveFilePath);
    Set<String> hosts = Files.loadResourceFile(is);
    return hosts;
  }

  private static String init(String confPath, String appId, Map<String, String> conf)
      throws Exception {
    // prepare context
    File confFile = new File(confPath);
    if (confFile.exists()) {
      Properties p = Util.buildProperties(confPath);
      WorkloadConf.set(p);
    }
    for (Entry<String, String> entry : conf.entrySet()) {
      WorkloadConf.set(entry.getKey(), entry.getValue());
    }
    String baseWorkPath = WorkloadConf.get(Constants.WORKLOAD_OUTPUT_PATH);
    if (baseWorkPath == null) {
      throw new Exception("configuration can't be loaded: " + Constants.WORKLOAD_OUTPUT_PATH);
    }
    File workDir = new File(baseWorkPath, appId);

    if (workDir.exists()) {
      System.err.println(workDir.getAbsolutePath() + " exists. delete it.");
      workDir.delete();
    }
    workDir.mkdirs();
    String workPath = workDir.getAbsolutePath() + "/";
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

  private static void diagnose(String workPath, Set<String> hosts) {
    try {
      List<DiagnosisResult> diagnosisResultList =
          Diagnostician.diagnose(buildDiagnosisContext(workPath, hosts));
      File analysisFile = new File(workPath, "analysis.result");
      FileUtil.printAnalysisResult(diagnosisResultList, analysisFile);
    } catch (Exception e) {
      System.out.println("WARN: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public static void execute(String confPath, String desPath, App app, Map<String, String> conf)
      throws Exception {

    String workPath = init(confPath, app.getAppId(), conf);

    // output parsed spark driver log to workPath.
    DriverlogMain.printApp(app, workPath);
    WorkloadConf.set(Constants.SPARK_CLUSTER_SLAVE,
        new File(workPath, "executors.csv").getAbsolutePath());
    Set<String> hosts = app.getExecutors();
    if (hosts == null || hosts.isEmpty()) {
      hosts = loadSlaveSet();
    }
    analyzePerformance(workPath, desPath, hosts, app.getStartTime(), app.getEndTime());

    diagnose(workPath, app.getExecutors());
  }

  public static void execute(String confPath, String desPath, String logPath) throws Exception {
    // parse spark driver log.
    App app = DriverlogMain.parseLogFile(logPath);
    execute(confPath, desPath, app, new HashMap<String, String>());
  }

  private static DiagnosisContext buildDiagnosisContext(String workPath, Set<String> hosts)
      throws Exception {
    CommandDes cd = (CommandDes) WorkloadContext.get("dstat");
    Map<String, List<Map<String, List<List<String>>>>> map =
        new HashMap<String, List<Map<String, List<List<String>>>>>();
    for (String host : hosts) {
      XChartPreprocessor xcp = new XChartPreprocessor();
      xcp.setCSVFolder(workPath);
      map.put(host, xcp.getDataList(cd, host));

    }
    DiagnosisContext ac = new DiagnosisContext();
    ac.putPerformanceData(map);
    return ac;
  }

  @Deprecated
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
    new AgentProxy(master, processor, new AppDes(hosts, "dstat"))
        .requestService(new HttpDstatServiceRequest(httpUrl, startTime, endTime));
    System.out.println("Load performance data file completely.");
    server.shutDown();
  }
}
