package com.maersk.aoplatform.basicactor;

import akka.actor.AbstractActor;
import akka.actor.AbstractLoggingActor;
import akka.japi.pf.ReceiveBuilder;

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
                .match(Add.class, this::onAdd)
                .match(Subtract.class,this::onSubtract)
                .build();
    }

    private void onAdd(Add add) {
        log().info("Adding " + add.getAmount());
        count += add.getAmount();
        log().info("count : " + count);

    }

    private void onSubtract(Subtract dec) {
        if (dec.getAmount() <= count) {
            log().info("Subtracting " + dec.getAmount());
            count -= dec.getAmount();
            log().info("count  : " + count);
        }

    }
}
