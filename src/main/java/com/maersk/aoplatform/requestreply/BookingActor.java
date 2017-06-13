package com.maersk.aoplatform.requestreply;

import akka.actor.AbstractActor;
import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.util.Timeout;
import scala.concurrent.duration.Duration;

/**
 * Created by gbadenhorst on 6/12/2017.
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import static akka.pattern.PatternsCS.ask;

public class BookingActor extends AbstractLoggingActor {

    private long bookingId = 1L;
    private ActorRef capacity;
    private ActorRef bookingStorage;


    public static class RequestBooking {

    }




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
        Timeout t = new Timeout(Duration.create(5, TimeUnit.SECONDS));
        String bookingIdAsStr = Long.toString(bookingId);
        CompletionStage<Object> stage = ask(capacity,new CapacityActor.CheckCapacity(),t);
        Object response = stage.toCompletableFuture().join();
        if( response instanceof  CapacityActor.CapacityAvailable ){
            bookingStorage.tell(new BookingStorageActor.AddBooking(bookingIdAsStr),getSelf());
            log().info("BookingActor : Booking created with id : " +  bookingIdAsStr);
            bookingId++;
        } else {
            log().error("BookingActor : Capacity Unavailable");
        }
        //CompletableFuture future= ask(capacity,new CapacityActor.CheckCapacity(),t).toCompletableFuture();


    }
}
