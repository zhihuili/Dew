package com.intel.sto.bigdata.app.sparklogparser.util;

import java.text.SimpleDateFormat;

public class Util {

  public static long transformTime(String dateStr) throws Exception {
    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    return format.parse("20" + dateStr).getTime();
  }
}
