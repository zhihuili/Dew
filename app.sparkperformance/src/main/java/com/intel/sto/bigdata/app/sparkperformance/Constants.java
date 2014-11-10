package com.intel.sto.bigdata.app.sparkperformance;

public class Constants {
  public static final String DSTAT_FILE = "_dstat.csv";
  public static final String JOB_NAME = "job";
  public static final String STAGE_NAME = "stage";
  public static final String TASK_NAME = "task";
  public static final String CSV_SUFFIX = ".csv";
  public static final String GRAPH_SUFFIX = ".jpg";
  public static final String DATA_SPLIT = ",";
  public static final String DSTAT_COMMAND = "dstat";

  /************* WorkloadEnv *************/
  public static final String SPARK_CLUSTER_SLAVE = "spark.cluster.slave";
  public static final String WORKLOAD_NAME = "workload.name";
  public static final String WORKLOAD_WORKDIR = "workload.workdir";
  public static final String WORKLOAD_STEP_RUNNER = "workload.step.runner";
  public static final String WORKLOAD_STEP_PARSER = "workload.step.parser";
  public static final String WORKLOAD_STEP_LOADER = "workload.step.loader";
  public static final String WORKLOAD_STEP_CLEANER = "workload.step.cleaner";
  public static final String WORKLOAD_STEP_CHART = "workload.step.chart";
  public static final String WORKLOAD_STEP_REPORT = "workload.step.report";
  public static final String WORKLOAD_OUTPUT_PATH = "workload.output.path";
  public static final String WORKLOAD_BACKUP_PATH = "workload.backup.path";
  public static final String WORKLOAD_CONF_PREFIX = "workload";
  public static final String WORKLOAD_PATH_SUFFIX = "path";
  public static final String WORKLOAD_RUN_SUFFIX = "run";
  public static final String WORKLOAD_EXECUTE_BACKUP = "workload.execute.backup";

  /**************** Runtime *****************/
  public static final String DRIVER_LOG_SUFFIX = "log.log";
  public static final String DRIVER_CSV_PAHT = "/tmp";
  public static final String WORKLOAD_LOG = "workload.log";
  public static final String WORKLOAD_RUNTIME = "workload.runtime";

  /**************** other conf ********************/
  public static final String WORKLOAD_RUNNER_COMMAND = "workload.runner.command";
  public static final String WORKLOAD_RUNNER_FREQUENCY = "workload.runner.frequency";
  public static final String WORKLOAD_RUNNER_SLEEPTIME = "workload.runner.sleeptime";
  public static final String WORKLOAD_CHART_XMAX = "workload.chart.xmax";
  public static final String WORKLOAD_CHART_WIDTH = "workload.chart.width";
  public static final String WORKLOAD_CHART_HEIGHT = "workload.chart.height";

}
