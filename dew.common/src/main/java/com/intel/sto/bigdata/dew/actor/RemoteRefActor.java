package com.intel.sto.bigdata.dew.actor;

import static java.util.concurrent.TimeUnit.SECONDS;
import scala.concurrent.duration.Duration;
import akka.actor.ActorIdentity;
import akka.actor.ActorRef;
import akka.actor.Identify;
import akka.actor.ReceiveTimeout;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;

public abstract class RemoteRefActor extends UntypedActor {
  protected ActorRef actor;
  protected String url;
  protected Procedure<Object> active;
  private LoggingAdapter log = Logging.getLogger(this);

  protected void sendIdentifyRequest() {
    getContext().actorSelection(url).tell(new Identify(url), getSelf());
    getContext()
        .system()
        .scheduler()
        .scheduleOnce(Duration.create(3, SECONDS), getSelf(), ReceiveTimeout.getInstance(),
            getContext().dispatcher(), getSelf());
  }

  @Override
  public void onReceive(Object message) throws Exception {
    if (message instanceof ActorIdentity) {
      actor = ((ActorIdentity) message).getRef();
      if (actor == null) {
        log.error(url + " is not available: ");
      } else {
        getContext().watch(actor);
        getContext().become(active, true);
        init();
      }
    } else if (message instanceof ReceiveTimeout) {
      sendIdentifyRequest();
    } else {
      log.warning(url + " is not ready yet");
    }
  }

  abstract protected void init();
}
