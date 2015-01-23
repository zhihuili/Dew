package com.intel.sto.bigdata.dew.actor;

import akka.actor.Props;

public class ActorDef {
  private Props props;
  private String name;

  public ActorDef(Props props, String name) {
    this.props = props;
    this.name = name;
  }

  public Props getProps() {
    return props;
  }

  public String getName() {
    return name;
  }

}
