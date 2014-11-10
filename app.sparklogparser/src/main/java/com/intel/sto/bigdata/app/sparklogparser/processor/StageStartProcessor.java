package com.intel.sto.bigdata.app.sparklogparser.processor;

import com.intel.sto.bigdata.app.sparklogparser.model.App;
import com.intel.sto.bigdata.app.sparklogparser.model.Job;
import com.intel.sto.bigdata.app.sparklogparser.model.Stage;

public class StageStartProcessor extends BaseProcessor {

  @Override
  public void process(App app) throws Exception {
    Job job = app.getLastChild();
    Stage stage = new Stage();
    job.addNewChild(stage);
    initNode(stage);
    stage.setStartTime(time);
    stage.setId(super.subLogLine(6));
    stage.setName(super.subLogLine(7).substring(1));
  }

}
