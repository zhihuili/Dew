package com.intel.sto.bigdata.app.webcenter.logic.action.history;

import java.io.File;
import java.util.ArrayList;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class ShowHistoryPicAction extends ActionSupport {

  private static final long serialVersionUID = 7941458215136661321L;
  public String path;
  public ArrayList<String> piclink;

  public ArrayList<String> getPiclink() {
    return piclink;
  }

  public void setPiclink(ArrayList<String> piclink) {
    this.piclink = piclink;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String execute() throws Exception {
    piclink = new ArrayList<String>();
    piclink = getpiclink(path);

    String webappPath =
        ServletActionContext.getServletContext().getRealPath(File.separator) + "/ForHistory";
    File historyPath = new File(webappPath);

    if (historyPath.exists()) {
      deleteAll(historyPath);
      historyPath.mkdir();
    } else {
      historyPath.mkdir();
    }

    ArrayList<String> wholePath = new ArrayList<String>();
    for (String tmp : piclink) {
      wholePath.add(path + "/" + tmp);
    }
    for (String jpgFile : wholePath) {
      String cmdT = "/bin/cp " + jpgFile + " " + webappPath;
      String[] cmdW = { "/bin/sh", "-c", cmdT };
      Process p = Runtime.getRuntime().exec(cmdW);
      if (p.waitFor() != 0) {
        throw new Exception("backup failed.");
      }
    }

    return SUCCESS;
  }

  static public ArrayList<String> getpiclink(String filePath) {
    ArrayList<String> temp = new ArrayList<String>();
    File root = new File(filePath);
    File[] files = root.listFiles();
    for (File file : files) {
      if (file.getName().endsWith(".jpg") || file.getName().endsWith(".png")) {
        temp.add(file.getName());
      }
    }

    return temp;
  }

  public void deleteAll(File path) {
    if (!path.exists())
      return;
    if (path.isFile()) {
      path.delete();
      return;
    }
    File[] files = path.listFiles();
    if (files.length != 0) {
      for (int i = 0; i < files.length; i++) {
        deleteAll(files[i]);
      }
    }
    path.delete();
  }
}
