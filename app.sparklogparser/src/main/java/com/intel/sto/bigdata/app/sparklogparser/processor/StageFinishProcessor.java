package com.intel.sto.bigdata.app.sparklogparser.processor;

import com.intel.sto.bigdata.app.sparklogparser.model.App;
import com.intel.sto.bigdata.app.sparklogparser.model.Stage;

public class StageFinishProcessor extends BaseProcessor {

  @Override
  public void process(App app) throws Exception {
    String stageId = super.subLogLine(5);
    Stage stage = super.findStage(app, stageId);
    stage.setEndTime(time);
    stage.setDuration(super.subLogLine(11));
  }

}
