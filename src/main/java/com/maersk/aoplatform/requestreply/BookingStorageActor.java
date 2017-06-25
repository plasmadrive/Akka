package com.maersk.aoplatform.requestreply;

import akka.actor.AbstractActor;
import akka.actor.AbstractLoggingActor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gbadenhorst on 6/12/2017.
 */
public class BookingStorageActor extends AbstractLoggingActor  {


    // Message to add a booking to storage (where booking datat is just a booking Id)
    public static class AddBooking {

        private String bookingId;

        public AddBooking(String bookingId) {
            this.bookingId = bookingId;
        }

        public String getBookingId() {
            return bookingId;
        }
    }


    private List bookingList;

    public BookingStorageActor() {
        this.bookingList = new ArrayList();
    }




    // createReceive needs to be overridden to return a Receive partial function usually using a ReceiveBuilder
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match (AddBooking.class, addBooking -> {
                    // AddMessage the booking message to the
                    this.bookingList.add(addBooking.getBookingId());
                    log().info("BookingStorageActor: Booking Added : " + addBooking.getBookingId());
                })
                .matchAny(
                        o  -> {
                            log().info("Unknown message received");
                        }
                )
                .build();
    }

}
