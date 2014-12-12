package com.intel.sto.bigdata.dew.service.logcollection.aggregator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.intel.sto.bigdata.dew.conf.DewConf;
import com.intel.sto.bigdata.dew.http.server.HttpStreamCallback;
import com.intel.sto.bigdata.dew.service.logcollection.Constants;

public class IndividualHdfsLogHttpCallback extends HttpStreamCallback {

  private DewConf dewConf;

  public IndividualHdfsLogHttpCallback(DewConf dewConf) {
    this.dewConf = dewConf;
  }

  @Override
  public void call(Map<String, String> parameters, InputStream is) {
    try {
      Configuration conf = new Configuration();
      // conf.addResource(new Path("/home/frank/Downloads/hadoop-1.0.3/conf/core-site.xml"));
      FileSystem fs = FileSystem.get(URI.create(dewConf.get("hdfs")), conf);
      Path logPath =
          new Path(File.separator + "dewlog" + File.separator + parameters.get(Constants.APP_ID));
      if (!fs.exists(logPath)) {
        fs.mkdirs(logPath);
      }
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      String hostName = parameters.get(Constants.HOST_NAME);
      String executorId = parameters.get(Constants.EXECUTOR_ID);
      String logFileName = parameters.get(Constants.LOG_FILE_NAME);
      Path logFilePath = new Path(logPath, hostName + "." + executorId + "." + logFileName);
      FSDataOutputStream os = fs.create(logFilePath);
      String line;
      while ((line = br.readLine()) != null) {
        os.write((line + System.getProperty("line.separator")).getBytes());
      }
      os.close();
      fs.close();
      br.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
