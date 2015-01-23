package com.intel.sto.bigdata.dew.actor;

import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.resume;
import scala.concurrent.duration.Duration;
import akka.actor.OneForOneStrategy;
import akka.actor.SupervisorStrategy;
import akka.actor.SupervisorStrategy.Directive;
import akka.actor.UntypedActor;
import akka.japi.Function;

public class DewSupervisor extends UntypedActor {
  private static SupervisorStrategy strategy = new OneForOneStrategy(10,
      Duration.create("1 minute"), new Function<Throwable, Directive>() {
        @Override
        public Directive apply(Throwable t) {
          if (t instanceof NullPointerException) {
            return resume();
          } else {
            return restart();
          }
        }
      });

  @Override
  public SupervisorStrategy supervisorStrategy() {
    return strategy;
  }

  public void onReceive(Object o) {
    if (o instanceof ActorDef) {
      ActorDef def = (ActorDef) o;
      getSender().tell(getContext().actorOf(def.getProps(), def.getName()), getSelf());
    } else {
      unhandled(o);
    }
  }
}
