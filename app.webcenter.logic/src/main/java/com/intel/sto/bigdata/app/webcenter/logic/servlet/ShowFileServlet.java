package com.intel.sto.bigdata.app.webcenter.logic.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.intel.sto.bigdata.app.webcenter.logic.util.Utils;

public class ShowFileServlet extends HttpServlet {
  private static final long serialVersionUID = 2750415374955185712L;

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html");
    String path = request.getParameter("path");

    File filePath = new File(path);
    if (!filePath.exists()) {
      filePath = new File(Utils.getWorkloadPath(), path);
    }

    FileInputStream fis = new FileInputStream(filePath);
    OutputStream os = response.getOutputStream();

    try {
      int count = 0;
      byte[] buffer = new byte[200 * 1024];
      while ((count = fis.read(buffer)) != -1)
        os.write(buffer, 0, count);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (os != null)
        os.close();
      if (fis != null)
        fis.close();
    }
  }
}
