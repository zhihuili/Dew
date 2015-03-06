package com.intel.sto.bigdata.app.asp.chart;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class Vm {
  public void createHtml(DataAdaptor data,String vm, Properties conf) throws FileNotFoundException {
    VelocityEngine ve = new VelocityEngine();
    String name = vm.split("/")[2].split("\\.")[0];
    System.out.println("name: " + name);
    ve.init();
    /* next, get the Template */
    Template t = ve.getTemplate(vm);
    /* create a context and add data */
    VelocityContext context = new VelocityContext();
    context.put("tabledata", data.mapGroup);
    context.put("avgdata", data.maplistAvg);
    context.put("filelist", data.listshowFile);
    context.put("workload", data.mapWorkload);
    context.put("grouplist", data.listGroup);
    context.put("platformlist", data.listPlatform);
    /* now render the template into a StringWriter */
    StringWriter writer = new StringWriter();
    String path = conf.getProperty("imagedir") + "../";
    PrintWriter out1 = new PrintWriter(path + name + ".html");
    t.merge(context, writer);
    out1.println(writer.toString());
    out1.close();
  }
}
