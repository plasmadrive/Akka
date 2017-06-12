package com.maersk.aoplatform.requestreply;

import akka.actor.AbstractActor;
import akka.actor.AbstractLoggingActor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gbadenhorst on 6/12/2017.
 */
public class BookingStorageActor extends AbstractLoggingActor  {
    private String bookingId;

    private List bookingList;

    public static class AddBooking {


    }

    public String getBookingId() {
        return bookingId;
    }

    public BookingStorageActor() {
        this.bookingList = new ArrayList();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(BookingStorageActor.AddBooking.class,addBooking -> {
                    this.bookingList.add(addBooking);
                    log().info("BookingStorageActor: Booking Added : ");
                })
                .matchAny(
                        o  -> {
                            log().info("Unknown message received");
                        }
                )
                .build();
    }
}
