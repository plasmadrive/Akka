package com.maersk.aoplatform.requestreply;

import akka.actor.AbstractActor;
import akka.actor.AbstractLoggingActor;

/**
 * Created by gbadenhorst on 6/12/2017.
 */
public class CapacityActor extends AbstractLoggingActor {


    public static class CheckCapacity {

    }

    public static class CapacityAvailable {

    }

    @Override
    public Receive createReceive() {
        return null;
    }
}
