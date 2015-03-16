package com.intel.sto.bigdata.app.asp.chart;

import java.util.Properties;

import com.intel.sto.bigdata.app.asp.util.Util;

public class Git {
  public static void push(Properties conf) throws Exception {
    String path = conf.getProperty("imagedir") + "../";
    String giturl = conf.getProperty("giturl");
    try {
      gitPull(path, giturl);
    } catch (Exception e1) {
      try {
        gitPull(path, giturl);
      } catch (Exception e2) {
        gitPull(path, giturl);
      }
    }
    Util.execute("git add index.html", null, path);
    Util.execute("git add result.html", null, path);
    Util.execute("git add result_time.html", null, path);
    Util.execute("git add result_release.html", null, path);
    Util.execute("git add about.html", null, path);
    Util.execute("git add plaf1.html", null, path);
    Util.execute("git add detail.html", null, path);
    // Util.execute("git add css", null, path);
    Util.execute("git add image", null, path);
    Util.execute("git commit -m commit", null, path);
    // Util.execute("git push origin gh-pages", null, path);
    try {
      gitPush(path, giturl);
    } catch (Exception e1) {
      try {
        gitPush(path, giturl);
      } catch (Exception e2) {
        gitPush(path, giturl);
      }
    }
  }

  private static void gitPull(String path, String giturl) throws Exception {
    Util.execute("git pull " + giturl + " gh-pages", null, path);
  }

  private static void gitPush(String path, String giturl) throws Exception {
    Util.execute("git push " + giturl, null, path);
  }
}
