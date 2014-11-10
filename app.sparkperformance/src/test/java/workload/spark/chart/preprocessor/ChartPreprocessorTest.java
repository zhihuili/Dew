package workload.spark.chart.preprocessor;



import java.util.List;

import com.intel.sto.bigdata.app.sparkperformance.BaseTestCase;
import com.intel.sto.bigdata.app.sparkperformance.Constants;
import com.intel.sto.bigdata.app.sparkperformance.Util;
import com.intel.sto.bigdata.app.sparkperformance.WorkloadConf;
import com.intel.sto.bigdata.app.sparkperformance.WorkloadContext;
import com.intel.sto.bigdata.app.sparkperformance.des.CommandDes;

import workload.spark.runner.XRunnerTest;

public class ChartPreprocessorTest extends BaseTestCase{
    XRunnerTest run = new XRunnerTest();
	public void testChartPreprocessor() throws Exception{
//		setUp();
//		run.testXRunner();
//		//System.out.println(testDataFolder);
//		String[] command = WorkloadConf.get(Constants.WORKLOAD_RUNNER_COMMAND).split(Constants.DATA_SPLIT);
//		for(int k = 0 ; k < command.length; k++){
//			XChartPreprocessor xp = new XChartPreprocessor();
//			xp.setCSVFolder(testDataFolder);
//			CommandDes cd = (CommandDes) WorkloadContext.get(command[k]);
//			List<String> slaves = Util.getSlavesHost();
//			for (String slave : slaves) {
//				WorkloadContext.put(slave + "_" + command[k],xp.getDataList(cd,slave));
//			}
//		}
	}
}

