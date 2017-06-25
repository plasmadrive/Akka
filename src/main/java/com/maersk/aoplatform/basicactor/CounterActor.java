package com.maersk.aoplatform.basicactor;

import akka.actor.AbstractLoggingActor;

/**
 * Created by g on 02/06/2017.
 */
public class CounterActor extends AbstractLoggingActor {



    private int count;


    public CounterActor() {
        this(0);
    }

    public CounterActor(int count) {
        this.count = count;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(AddMessage.class, this::onAdd)
                .match(SubtractMessage.class,this::onSubtract)
                .build();
    }

    private void onAdd(AddMessage add) {
        log().info("Adding " + add.getAmount());
        count += add.getAmount();
        log().info("count : " + count);

    }

    private void onSubtract(SubtractMessage dec) {
        if (dec.getAmount() <= count) {
            log().info("Subtracting " + dec.getAmount());
            count -= dec.getAmount();
            log().info("count  : " + count);
        } else {
            log().error("CounterActor: subtract: requested subtract amount " + dec.getAmount() + " is greater than current total " + this.count);
        }

    }
}
