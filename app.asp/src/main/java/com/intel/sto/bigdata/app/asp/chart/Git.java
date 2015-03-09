package com.intel.sto.bigdata.app.asp.chart;

import java.util.Properties;

import com.intel.sto.bigdata.app.asp.util.Util;

public class Git {
  public static void push(Properties conf) throws Exception {
    String path = conf.getProperty("imagedir") + "../";
    String giturl = conf.getProperty("giturl");
    Util.execute("git pull " + giturl + " gh-pages", null, path);
    Util.execute("git add index.html", null, path);
    Util.execute("git add result.html", null, path);
    Util.execute("git add result_time.html", null, path);
    Util.execute("git add result_release.html", null, path);
    Util.execute("git add about.html", null, path);
    Util.execute("git add plaf1.html", null, path);
    // Util.execute("git add css", null, path);
    Util.execute("git add image", null, path);
    Util.execute("git commit -m commit", null, path);
    // Util.execute("git push origin gh-pages", null, path);
    Util.execute("git push " + giturl, null, path);
  }
}
