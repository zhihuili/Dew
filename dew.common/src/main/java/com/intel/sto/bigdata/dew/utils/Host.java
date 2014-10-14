package com.intel.sto.bigdata.dew.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Host {

  private static String ip;
  private static String hostName;

  static {
    try {
      InetAddress netAddress = InetAddress.getLocalHost();
      ip = netAddress == null ? null : netAddress.getHostAddress();
      hostName = netAddress == null ? null : netAddress.getHostName();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  public static String getIp() {
    return ip;
  }

  public static String getName() {
    return hostName;
  }

  public static void main(String[] args) {
    System.out.println(ip);
    System.out.println(hostName);
  }

}
