package com.intel.sto.bigdata.app.sparkperformance.ui;

import java.util.Properties;

import com.intel.sto.bigdata.app.sparkperformance.Constants;
import com.intel.sto.bigdata.app.sparkperformance.Util;
import com.intel.sto.bigdata.app.sparkperformance.WorkloadConf;
import com.intel.sto.bigdata.app.sparkperformance.backup.Backup;
import com.intel.sto.bigdata.app.sparkperformance.backup.Init;
import com.intel.sto.bigdata.app.sparkperformance.chart.ChartFactory;
import com.intel.sto.bigdata.app.sparkperformance.data.LoaderFactory;
import com.intel.sto.bigdata.app.sparkperformance.data.cleaner.CleanerFactory;
import com.intel.sto.bigdata.app.sparkperformance.data.parser.LogParserFactory;
import com.intel.sto.bigdata.app.sparkperformance.run.RunnerFactory;
import com.intel.sto.bigdata.app.sparkperformance.ui.report.ReportFactory;

/**
 * Execute special workload.
 * 
 */
public class Executor {

  public void execute(String[] args) throws Exception {
    Properties p = Util.buildProperties(args[0]);// configuration file path
    p.put(Constants.WORKLOAD_NAME, args[1]);
    p.put(Constants.WORKLOAD_WORKDIR, args[2]);
    WorkloadConf.set(p);
    Init.init();
    RunnerFactory.getRunner().run();
    LogParserFactory.getLogParser().parse();
    LoaderFactory.getLoader().loadData();
    CleanerFactory.getCleaner().clean();
    ChartFactory.getChart().createChart();
    ReportFactory.getReport();
    Backup.backup();
    WorkloadConf.clean();
  }
  //
  // /**
  // * build properties for every execution, support runtime load configuration.
  // *
  // * @param conf
  // * @return
  // * @throws IOException
  // */
  // private Properties buildProperties(String conf) throws IOException {
  // InputStream in = new BufferedInputStream(new FileInputStream(conf));
  // Properties props = new Properties();
  // props.load(in);
  // in.close();
  // return props;
  // }
}
