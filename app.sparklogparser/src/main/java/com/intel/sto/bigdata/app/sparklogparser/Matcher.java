package com.intel.sto.bigdata.app.sparklogparser;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.intel.sto.bigdata.app.sparklogparser.processor.*;

public class Matcher {

  static Map<String, Processor> map = new HashMap<String, Processor>();
  static {
    map.put("(.*)Starting job(.*)", new JobStartProcessor());
    map.put("(.*)Submitting Stage(.*)", new StageStartProcessor());
    // map.put("(.*)Starting task(.*)", new TaskStartProcessor());
    map.put("(.*)Submitting ([0-9]*) missing tasks from Stage(.*)", new TaskSetProcessor());
    map.put("(.*)Adding task set(.*)", new TaskSetAddProcessor());
    map.put("(.*)Starting task(.*)as TID(.*)", new TaskStartProcessor());
    map.put("(.*)TaskSetManager: Starting task(.*) in stage (.*)TID (.*)",
        new TaskStart1_1Processor());
    map.put("(.*)TaskSetManager: Finished task(.*) in stage (.*)TID (.*)",
        new TaskFinish1_1Processor());

    map.put("(.*)TaskSetManager: Finished TID(.*)", new TaskFinishProcessor());
    map.put("(.*)DAGScheduler: Stage(.*)finished in(.*)", new StageFinishProcessor());
    map.put("(.*)Job ([0-9]*)(.*)finished(.*)", new JobFinishProcessor());
    map.put("(.*)BlockManagerMasterActor.BlockManagerInfo: Added rdd(.*)", new MemProcessor());
    map.put("(.*)Registered executor:(.*)", new ExecutorsProcessor());
    map.put("(.*)Changing view acls to(.*)", new UserProcessor());
    map.put("(.*)Connected to Spark cluster with app ID(.*)", new StandaloneAppIdProcessor());
  }

  public static Processor build(String line) throws Exception {
    for (Entry<String, Processor> entry : map.entrySet()) {
      if (line.matches(entry.getKey())) {
        return entry.getValue();
      }
    }
    return null;
  }
}
