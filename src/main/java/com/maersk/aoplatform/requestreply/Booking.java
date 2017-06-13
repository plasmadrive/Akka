package com.maersk.aoplatform.requestreply;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * Created by gbadenhorst on 6/12/2017.
 */
public class Booking {
    static class RequestBooking {

    }



    public static void main(String[] args) throws Exception {
        final ActorSystem system = ActorSystem.create("system");
        final ActorRef  capacityActor = system.actorOf(Props.create(CapacityActor.class),"capacityActor");
        final ActorRef  bookingStorageActor = system.actorOf(Props.create(BookingStorageActor.class),"bookdingStorageActor");
        final ActorRef bookingActor = system.actorOf(Props.create(BookingActor.class,
                () -> new BookingActor(capacityActor,bookingStorageActor)),"bookingActor");

        bookingActor.tell(new BookingActor.RequestBooking(), ActorRef.noSender());
        bookingActor.tell(new BookingActor.RequestBooking(), ActorRef.noSender());
        bookingActor.tell(new BookingActor.RequestBooking(), ActorRef.noSender());
        bookingActor.tell(new BookingActor.RequestBooking(), ActorRef.noSender());
        bookingActor.tell(new BookingActor.RequestBooking(), ActorRef.noSender());

        Thread.sleep(10000);
        system.terminate();


    }
}
