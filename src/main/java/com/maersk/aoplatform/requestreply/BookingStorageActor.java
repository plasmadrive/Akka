package com.maersk.aoplatform.requestreply;

import akka.actor.AbstractActor;
import akka.actor.AbstractLoggingActor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gbadenhorst on 6/12/2017.
 */
public class BookingStorageActor extends AbstractLoggingActor  {


    private List bookingList;

    public static class AddBooking {

        private String bookingId;

        public AddBooking(String bookingId) {
            this.bookingId = bookingId;
        }

        public String getBookingId() {
            return bookingId;
        }
    }



    public BookingStorageActor() {
        this.bookingList = new ArrayList();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(BookingStorageActor.AddBooking.class,addBooking -> {

                })
                .match (AddBooking.class, addBooking -> {
                    this.bookingList.add(addBooking);
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
