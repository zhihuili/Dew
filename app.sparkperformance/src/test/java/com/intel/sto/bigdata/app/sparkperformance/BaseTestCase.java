package com.intel.sto.bigdata.app.sparkperformance;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.intel.sto.bigdata.app.sparkperformance.Constants;
import com.intel.sto.bigdata.app.sparkperformance.WorkloadConf;

import junit.framework.TestCase;

public abstract class BaseTestCase extends TestCase {
	protected String testDataFolder = "src/test/data/";
	protected String conf = "src/test/conf/conf.properties.template";
	public void setUp() throws IOException{
		InputStream in = new BufferedInputStream(new FileInputStream(conf));
		Properties p = new Properties();
		p.load(in);
		in.close();
		p.put(Constants.WORKLOAD_NAME, "nweight");
		p.put(Constants.WORKLOAD_WORKDIR, "src/test/");
		WorkloadConf.set(p);
	}
}
