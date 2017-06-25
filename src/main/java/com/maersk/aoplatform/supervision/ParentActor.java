package com.maersk.aoplatform.supervision;

import akka.actor.*;
import akka.japi.pf.DeciderBuilder;
import scala.concurrent.duration.Duration;
import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.resume;
import static akka.actor.SupervisorStrategy.stop;
import static akka.actor.SupervisorStrategy.escalate;


/**
 * Created by g on 14/06/2017.
 */
public class ParentActor extends AbstractLoggingActor {

    private ActorRef childActor;

    // Called before starting ParentActor
    // Create the child actor in this method
    @Override
    public void preStart() throws Exception {
        super.preStart();
        childActor = context().actorOf(Props.create(ChildActor.class),"ChildActor");
        Thread.sleep(100);
    }




    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchAny(o -> {
                    log().info("ParentActor : received " + o);
                    childActor.tell(o,getSelf()); // pass the message along telling the child we're the sender

                })
                .build();
    }

    // Manage the supervision
    @Override
    public SupervisorStrategy supervisorStrategy() {
        return new OneForOneStrategy(false, DeciderBuilder
                .match(RestartException.class, e -> restart())
                .match(ResumeException.class, e -> resume())
                .match(StopException.class, e -> stop())
                .matchAny(e -> escalate())
                .build()
                );

    }
}
