package com.maersk.aoplatform.persistence;

import akka.persistence.AbstractPersistentActor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by g on 14/06/2017.
 */
public class BookingActor extends AbstractPersistentActor {


    // Commands
    static class BookingCmd implements Serializable {

    }

    static class UpdateDepartureDateCmd extends BookingCmd implements Serializable {

        private Date departureDate;

        public UpdateDepartureDateCmd(Date departureDate) {
            this.departureDate = departureDate;
        }

        public Date getDepartureDate() {
            return departureDate;
        }
    }

    static class AddContainerCmd extends BookingCmd implements Serializable {
        private String containerId;

        public AddContainerCmd(String containerId) {
            super();
            this.containerId = containerId;
        }

        public String getContainerId() {
            return containerId;
        }
    }


    static class BookingEvt implements Serializable {

    }

    static class DapartureDateUpdatedEvt extends BookingEvt implements Serializable {
        private Date departureDate;

        public DapartureDateUpdatedEvt(Date departureDate) {
            this.departureDate = departureDate;
        }

        public Date getDepartureDate() {
            return departureDate;
        }
    }

    static class ContainerAddedBookingEvt extends BookingEvt implements Serializable {
        private String containerId;

        public ContainerAddedBookingEvt(String containerId) {
            this.containerId = containerId;
        }

        public String getContainerId() {
            return containerId;
        }
    }


    static class State implements Serializable {
        private Date departureDate;
        private List<String> containers;

        public State() {
            this.departureDate = null;
            this.containers = new ArrayList<>();
        }

        public State(Date departureDate, List<String> containers) {
            this.departureDate = departureDate;
            this.containers = containers;
        }

        public Date getDepartureDate() {
            return departureDate;
        }

        public void setDepartureDate(Date departureDate) {
            this.departureDate = departureDate;
        }

        public void addContainer(String pContainerId) {
            this.containers.add(pContainerId);
        }

        public void update(DapartureDateUpdatedEvt e) {
            this.departureDate = e.getDepartureDate();
        }

        public void update(ContainerAddedBookingEvt e) {
            this.addContainer(e.getContainerId());
        }


    }

    private State state = new State();


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(UpdateDepartureDateCmd.class, this::updateDepartureDate)
                .match(AddContainerCmd.class, this::addContainer)
                .matchEquals("print", s-> {
                    System.out.println(state);})
                .matchAny(c -> {
                    throw new Exception("Unknown message");
                })
                .build();
    }

    @Override
    public Receive createReceiveRecover() {
        return receiveBuilder()
                .match(BookingActor.DapartureDateUpdatedEvt.class, state::update)
                .match(ContainerAddedBookingEvt.class, state::update)
                .build();
    }

    private void updateDepartureDate(UpdateDepartureDateCmd cmd) {
        DapartureDateUpdatedEvt evt = new DapartureDateUpdatedEvt(cmd.getDepartureDate());


        persist(evt, (DapartureDateUpdatedEvt e) -> {
            state.update(e);
            getContext().getSystem().eventStream().publish(e);

        });
    }

    private void addContainer(AddContainerCmd cmd) {
        ContainerAddedBookingEvt evt = new ContainerAddedBookingEvt(cmd.getContainerId());
        persist(evt, (ContainerAddedBookingEvt e) -> {

            state.update(e);
            getContext().getSystem().eventStream().publish(e);
        });
    }



    @Override
    public String persistenceId() {
        return "booking-actor";
    }
}
