package com.intel.sto.bigdata.app.logmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.intel.sto.bigdata.dew.app.AgentProxy;
import com.intel.sto.bigdata.dew.app.AppDes;
import com.intel.sto.bigdata.dew.app.DoNothingAppProcessor;
import com.intel.sto.bigdata.dew.service.logcollection.aggregator.FirstAggregatorProcessor;
import com.intel.sto.bigdata.dew.service.logcollection.message.LogAggregatorRequest;
import com.intel.sto.bigdata.dew.service.logcollection.message.LogCollectionRequest;
import com.intel.sto.bigdata.dew.utils.Files;

public class LogCollection {

  private static Map<String, String> conf;

  public static void collect(String appId) throws Exception {
    init();
    FirstAggregatorProcessor processor = new FirstAggregatorProcessor();
    new AgentProxy(conf.get(Constants.DEW_MASTER_URL), processor,
        new AppDes(null, "logaggregation")).requestService(new LogAggregatorRequest());
    String aggregatorHttpUrl = processor.getHttpUrl();
    if (aggregatorHttpUrl == null) {
      throw new Exception("can't find log aggregator.");
    }
    LogCollectionRequest collectionRequest = new LogCollectionRequest();
    collectionRequest.setAppId(appId);
    collectionRequest.setHttpUrl(aggregatorHttpUrl);
    List<String> logPathList = new ArrayList<String>();
    // TODO path
    logPathList.add(conf.get(Constants.SPARK_LOG_PATH));
    collectionRequest.setLogPathList(logPathList);
    // TODO hosts
    new AgentProxy(conf.get(Constants.DEW_MASTER_URL), new DoNothingAppProcessor(), new AppDes(
        null, "logcollection")).requestService(collectionRequest);
  }

  private static void init() throws Exception {
    conf = Files.loadPropertiesFile("/logmanager.conf");
  }
  
  public static void main(String[] args) throws Exception{
    collect("app-20140729132215-0003");
  }
}
