package com.intel.sto.bigdata.dew.app;

import java.util.List;

import com.intel.sto.bigdata.dew.message.ServiceResponse;

public interface AppProcessor {
  void process(List<ServiceResponse> responseList);
}
