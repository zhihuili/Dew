package com.intel.sto.bigdata.dew.service.shellexecutor.message;

import com.intel.sto.bigdata.dew.message.ServiceRequest;

public class ExecuteRequest extends ServiceRequest {

  private static final long serialVersionUID = -4934953869764108247L;
  private String id;
  private String statusUrl;
  private String logUrl;
  private String directory;
  private String command;
  private String[] envp;

  public ExecuteRequest() {
    super("shell", "get");
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getStatusUrl() {
    return statusUrl;
  }

  public void setStatusUrl(String statusUrl) {
    this.statusUrl = statusUrl;
  }

  public String getLogUrl() {
    return logUrl;
  }

  public void setLogUrl(String logUrl) {
    this.logUrl = logUrl;
  }

  public String getDirectory() {
    return directory;
  }

  public void setDirectory(String directory) {
    this.directory = directory;
  }

  public String getCommand() {
    return command;
  }

  public void setCommand(String command) {
    this.command = command;
  }

  public String[] getEnvp() {
    return envp;
  }

  public void setEnvp(String[] envp) {
    this.envp = envp;
  }

}
