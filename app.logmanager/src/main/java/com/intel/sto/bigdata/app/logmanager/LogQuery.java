package com.intel.sto.bigdata.app.logmanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.intel.sto.bigdata.dew.service.logcollection.Constants;
import com.intel.sto.bigdata.dew.utils.Hdfs;

public class LogQuery {
  /**
   * query log file from hdfs only in current node.
   * 
   * @param appId
   * @param words
   * @throws Exception
   */
  public static List<String> querySingle(String appId, String words) throws Exception {
    List<String> result = new LinkedList<String>();
    FileSystem fs = Hdfs.getFileSystem();
    FileStatus[] files = findFile(fs, appId);
    for (FileStatus file : files) {
      if (!file.isDir()) {
        FSDataInputStream in = fs.open(file.getPath());
        String fileName = file.getPath().getName();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = br.readLine()) != null) {
          if (line.contains(words)) {
            result.add(fileName + " " + line);
          }
        }
        br.close();
      }
    }
    return result;
  }

  /**
   * query log file from hdfs distributed.
   * 
   * @param appId
   * @param words
   * @throws Exception
   */
  public static void queryDistributed(String appId, String words) throws Exception {
    FileSystem fs = Hdfs.getFileSystem();
    FileStatus[] files = findFile(fs, appId);
    for (FileStatus file : files) {
      long blockSize = file.getBlockSize();
      long fileLength = file.getLen();
      if (blockSize < fileLength) {
        // FIXME
        // throw new Exception(file.getPath().getName()
        // + " lenght is larger than block size. It's not supported now, please fix me.");
      }
      BlockLocation[] locations = fs.getFileBlockLocations(file, 0, 1);
      // TODO unfinished
    }
  }

  private static FileStatus[] findFile(FileSystem fs, String appId) throws Exception {

    Path logPath = new Path(File.separator + Constants.LOG_ROOT_DIR + File.separator + appId);
    if (!fs.exists(logPath)) {
      throw new Exception(appId + "'s log file is not exists.");
    }
    FileStatus[] files = fs.listStatus(logPath);
    return files;
  }
}
