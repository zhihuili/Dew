package com.intel.sto.bigdata.app.webcenter.logic.action.history;

import java.io.File;
import java.util.ArrayList;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class ShowHistoryPicAction extends ActionSupport {

  private static final long serialVersionUID = 7941458215136661321L;
  public String path;
  public ArrayList<String> picLink;

  public ArrayList<String> getPiclink() {
    return picLink;
  }

  public void setPiclink(ArrayList<String> piclink) {
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

    return SUCCESS;
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
