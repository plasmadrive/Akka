package com.maersk.aoplatform.requestreply;

import akka.actor.AbstractActor;
import akka.actor.AbstractLoggingActor;

import java.util.Random;

/**
 * Created by gbadenhorst on 6/12/2017.
 */
public class CapacityActor extends AbstractLoggingActor {



    // Request message to check capacity is available for the booking
    public static class CheckCapacity {

    }

    // Response message when capacity is available
    public static class CapacityAvailable {

    }


    // Response message when capacity is unavailable
    public static class CapacityUnavailable {

    }

    private Random random;

    public CapacityActor() {
        random = new Random();
    }


    // createReceive needs to be overridden to return a Receive partial function usually using a ReceiveBuilder
    @Override
    public Receive createReceive() {

        return receiveBuilder()
                .match(CheckCapacity.class,this::checkCapacity)
                .matchAny(o-> log().error("CapacityActor : unexpected message"))
                .build();
    }

    private void checkCapacity (CapacityActor.CheckCapacity capacityCheck) {
        // Capacity is unavailable 1 time in 10
        final boolean capacityUnavailable  = random.nextInt() % 10 == 0;

        if (capacityUnavailable) {
            // if capacity is unavailable send  CapacityUnavailable message to the sender of the CheckCapacity message
            getSender().tell(new CapacityUnavailable(),getSelf());
            log().info("CapacityActor : Capactity Not Avaiable");


        } else {
            // if capacity is available send a CapacityAvailable message to the sender of the CheckCapacity message
            getSender().tell(new CapacityAvailable(), getSelf());
            log().info("CapacityActor : Capacity OK");
        }
    }
}
