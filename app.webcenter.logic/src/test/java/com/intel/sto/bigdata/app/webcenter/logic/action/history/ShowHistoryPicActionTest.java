package com.intel.sto.bigdata.app.webcenter.logic.action.history;

import junit.framework.TestCase;

public class ShowHistoryPicActionTest extends TestCase {
  ShowHistoryPicAction action = new ShowHistoryPicAction();

  @Override
  public void setUp() {
    action.setPath("/tmp/application_1427779139329_0009");
  }

  public void testPicAction() {
    try {
      action.execute();
      for (String link : action.getPiclink()) {
        System.out.println(link);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
