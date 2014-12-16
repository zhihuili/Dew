package com.intel.sto.bigdata.dew.utils;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

import com.intel.sto.bigdata.dew.conf.DewConf;

public class Hdfs {

  private static FileSystem fs;

  private Hdfs() throws IOException {
    this(new DewConf());
  }

  private Hdfs(DewConf dewConf) throws IOException {
    Configuration conf = new Configuration();
    // conf.addResource(new Path("/home/frank/Downloads/hadoop-1.0.3/conf/core-site.xml"));
    fs = FileSystem.get(URI.create(dewConf.get("hdfs")), conf);
  }

  /**
   * for app
   * 
   * @return
   * @throws IOException
   */
  public static FileSystem getFileSystem() throws IOException {
    if (fs == null) {
      new Hdfs();
    }
    return fs;
  }

  /**
   * for service
   * 
   * @param dewConf
   * @return
   * @throws IOException
   */
  public static FileSystem getFileSystem(DewConf dewConf) throws IOException {
    if (fs == null) {
      new Hdfs(dewConf);
    }
    return fs;
  }
}
