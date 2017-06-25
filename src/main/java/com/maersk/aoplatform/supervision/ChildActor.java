package com.maersk.aoplatform.supervision;

import akka.actor.AbstractActor;
import akka.actor.AbstractLoggingActor;
import scala.Option;

import java.util.Optional;


/**
 * Created by g on 14/06/2017.
 */
public class ChildActor extends AbstractLoggingActor  {

    private int counter = 0;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> s.equals("Increment"), s -> {
                            counter++;
                            log().info("ChildActor : Counter = " + counter);
                        }

                )
                .match(String.class, s -> s.equals("Restart"), s -> {
                    log().error("ChildActor : Restart message received");
                    throw new com.maersk.aoplatform.supervision.RestartException();

                })
                .match(String.class, s -> s.equals("Resume"), s -> {
                    log().error("ChildActor : Resume message received");
                    throw new ResumeException();
                })
                .match(String.class, s -> s.equals("Stop"), s -> {
                    log().error("ChildActor : Stop message received");
                    throw new StopException();
                })
                .build();
    }

    @Override
    public void preRestart(Throwable reason, Optional<Object> message) throws Exception {
        log().info("ChildActor: Restarting");
        super.preRestart(reason, message);
    }

    @Override
    public void postStop() throws Exception {
        log().info("ChildActor: Stopping");
        super.postStop();
    }
}
