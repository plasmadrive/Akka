package com.maersk.aoplatform.requestreply;

import akka.actor.AbstractActor;
import akka.actor.AbstractLoggingActor;

/**
 * Created by gbadenhorst on 6/12/2017.
 */
public class BookingActor extends AbstractLoggingActor {

    public static class RequestBooking {

    }

    public static class BookingSuccessfulResponse {
        private String bookingId;
        public BookingSuccessfulResponse(String pBookingId) {
            this.bookingId = pBookingId;
        }

        public String getBookingId() {
            return bookingId;
        }
    }

    public static class BookingFailureResponse {
        private String errorMsg;

        public BookingFailureResponse(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public String getErrorMsg() {
            return errorMsg;
        }
    }

    @Override
    public Receive createReceive() {
        return null;
    }
}
