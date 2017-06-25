package com.maersk.aoplatform.requestreply;

import akka.actor.AbstractActor;
import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.util.Timeout;
import scala.Serializable;
import scala.concurrent.duration.Duration;

/**
 * Created by gbadenhorst on 6/12/2017.
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import static akka.pattern.PatternsCS.ask;

public class BookingActor extends AbstractLoggingActor {

    //  Message to request booking.  No booking data
    public static class RequestBooking implements Serializable {
        // should contain booking request data

    }

    // keep a count of booking id
    private long bookingId = 1L;
    // reference to a  CapactityActor
    private ActorRef capacity;
    // reference to a Booking
    private ActorRef bookingStorage;



    public BookingActor(ActorRef capacity, ActorRef bookingStorage) {
        this.capacity = capacity;
        this.bookingStorage = bookingStorage;
    }


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(RequestBooking.class, this::processBooking)
                .matchAny(o ->log().error("BookingActor : unexpected messsage"))
                .build();
    }



    private void processBooking(RequestBooking requestBooking){
        // set a timeout of 5 seconds
        Timeout t = new Timeout(Duration.create(5, TimeUnit.SECONDS));

        String bookingIdAsStr = Long.toString(bookingId);

        // Ask the capacity actor if there is sufficient capacity  and get back a completion stage
        CompletionStage<Object> stage = ask(capacity,new CapacityActor.CheckCapacity(),t);

        // Get the result
        Object response = stage.toCompletableFuture().join();


        if( response instanceof  CapacityActor.CapacityAvailable ){

            // Store the booking using the booking storage actor
            bookingStorage.tell(new BookingStorageActor.AddBooking(bookingIdAsStr),getSelf());
            log().info("BookingActor : Booking created with id : " +  bookingIdAsStr);

            // increment the latest available booking id
            bookingId++;
        } else {
            log().error("BookingActor : Capacity Unavailable");
        }


    }
}
