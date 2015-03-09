package com.intel.sto.bigdata.app.asp.chart;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class Vm {
  public void createHtml(Data data, String vm, Properties conf) throws FileNotFoundException {
    VelocityEngine ve = new VelocityEngine();
    String name = vm.split("/")[2].split("\\.")[0];
    System.out.println("name: " + name);
    ve.init();
    /* next, get the Template */
    Template t = ve.getTemplate(vm);
    /* create a context and add data */
    VelocityContext context = new VelocityContext();
    context.put("tabledata", data.getGroupDetail());
    context.put("avgdata", data.getNewGroupAvg());
    context.put("filelist", data.getFileList().subList(1, data.getFileList().size()));
    context.put("workload", data.getGroupLines());
    context.put("grouplist", data.getGroupList());
    context.put("platform", data.getPlat());
    /* now render the template into a StringWriter */
    StringWriter writer = new StringWriter();
    String path = conf.getProperty("imagedir") + "../";
    PrintWriter out1 = new PrintWriter(path + name + ".html");
    PrintWriter out2 = new PrintWriter("./WEB/" + name + ".html");
    t.merge(context, writer);
    out1.println(writer.toString());
    out2.println(writer.toString());
    out1.close();
    out2.close();
  }
}
