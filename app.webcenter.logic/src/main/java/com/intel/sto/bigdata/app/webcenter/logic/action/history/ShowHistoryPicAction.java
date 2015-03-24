package com.intel.sto.bigdata.app.webcenter.logic.action.history;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;

public class ShowHistoryPicAction extends ActionSupport {

  private static final long serialVersionUID = 7941458215136661321L;
  private String path;
  private List<String> picLink;

  public List<String> getPiclink() {
    return picLink;
  }

  public void setPiclink(List<String> piclink) {
    this.picLink = piclink;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String execute() throws Exception {
    picLink = new ArrayList<String>();
    picLink = getPicLink(path);
    sortPicLink(picLink);
    return SUCCESS;
  }

  private void sortPicLink(List<String> picLink) {
    Collections.sort(picLink, new Comparator<String>() {
      public int compare(String o1, String o2) {
        String[] o1a = parseString(o1);
        String[] o2a = parseString(o2);
        if (o1a.length < o2a.length) {
          return -1;
        } else {
          if (o1a.length > o2a.length) {
            return 1;
          } else {
            if (o1a.length == 1) {
              return o1a[0].compareTo(o2a[0]);
            } else {
              if (o1a[0].equals(o2a[0])) {
                return o1a[1].compareTo(o2a[1]);
              } else {
                return o1a[0].compareTo(o2a[0]);
              }
            }
          }
        }
      }
    });

  }

  private String[] parseString(String string) {
    String[] pathSplit = string.split("\\/");
    String fileName = pathSplit[pathSplit.length - 1];
    String name = fileName.split("\\.")[0];
    String[] nameSplit = name.split("_");
    if (nameSplit.length == 3) {
      String type = nameSplit[1];
      String host = nameSplit[2];
      return new String[] { type, host };
    } else {
      return nameSplit;
    }
  }

  static public ArrayList<String> getPicLink(String filePath) {
    ArrayList<String> temp = new ArrayList<String>();
    File root = new File(filePath);
    File[] files = root.listFiles();
    for (File file : files) {
      if (file.getName().endsWith(".jpg") || file.getName().endsWith(".png")) {
        temp.add(file.getAbsolutePath());
      }
    }

    return temp;
  }

}
