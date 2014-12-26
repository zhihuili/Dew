package com.intel.sto.bigdata.app.logmanager;

import java.io.File;

import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.intel.sto.bigdata.dew.service.logcollection.Constants;
import com.intel.sto.bigdata.dew.utils.Hdfs;

public class LogQuery {
  public static void query(String appId, String words) throws Exception {
    FileSystem fs = Hdfs.getFileSystem();
    Path logPath = new Path(File.separator + Constants.LOG_ROOT_DIR + File.separator + appId);
    if (!fs.exists(logPath)) {
      throw new Exception(appId + "'s log file is not exists.");
    }
    FileStatus[] files = fs.listStatus(logPath);
    for (FileStatus file : files) {
      long blockSize = file.getBlockSize();
      long fileLength = file.getLen();
      if (blockSize < fileLength) {
        // FIXME
        throw new Exception(file.getPath().getName()
            + " lenght is larger than block size. It's not supported now, please fix me.");
      }
      BlockLocation[] locations = fs.getFileBlockLocations(file, 0, 1);
    }
  }
}
