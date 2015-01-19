package com.intel.sto.bigdata.dew.utils;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

import com.intel.sto.bigdata.dew.conf.DewConf;

public class Hdfs {

  private static FileSystem fs;

  private Hdfs() throws IOException {
    this(DewConf.getDewConf());
  }

  private Hdfs(DewConf dewConf) {
    Configuration conf = new Configuration();
    conf.set("fs.default.name", dewConf.get("hdfs"));
    conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
    try {
      fs = FileSystem.get(conf);
    } catch (Throwable t) {
      System.err.println("----------hdfs throw:" + t);
      t.printStackTrace();
    }
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
