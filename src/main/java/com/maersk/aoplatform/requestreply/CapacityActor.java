package com.maersk.aoplatform.requestreply;

import akka.actor.AbstractActor;
import akka.actor.AbstractLoggingActor;

import java.util.Random;

/**
 * Created by gbadenhorst on 6/12/2017.
 */
public class CapacityActor extends AbstractLoggingActor {

    private Random random;


    public static class CheckCapacity {

    }

    public static class CapacityAvailable {

    }

    public static class CapacityUnavailable {

    }

    public CapacityActor() {
        random = new Random();
    }

    @Override
    public Receive createReceive() {



        return receiveBuilder()
                .match(CheckCapacity.class,this::checkCapacity )
                .matchAny(o-> log().error("CapacityActor : unexpected message"))
                .build();
    }

    private void checkCapacity (CapacityActor.CheckCapacity capacityCheck) {
        final boolean capacityUnavailable  = random.nextInt() % 10 == 0;
        if (capacityUnavailable) {
            getSender().tell(new CapacityUnavailable(),getSelf());
            log().info("CapacityActor : Capactity Not Avaiable");


        } else {
            getSender().tell(new CapacityAvailable(), getSelf());
            log().info("CapacityActor : Capacity OK");
        }
    }
}
