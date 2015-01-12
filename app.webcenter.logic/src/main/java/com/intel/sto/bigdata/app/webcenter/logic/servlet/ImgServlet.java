package com.intel.sto.bigdata.app.webcenter.logic.servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImgServlet extends HttpServlet {

  private static final long serialVersionUID = 7228565490503881066L;

  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("image/jpeg");
    String fileName = request.getParameter("fileName");

    FileInputStream fis = new FileInputStream(fileName);
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
