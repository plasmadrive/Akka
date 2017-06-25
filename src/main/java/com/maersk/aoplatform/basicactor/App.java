package com.maersk.aoplatform.basicactor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * Created by g on 02/06/2017.
 */
public class App {

    public static void main(String[] args) {

        final ActorSystem system = ActorSystem.create("system");
        final ActorRef counter = system.actorOf(Props.create(CounterActor.class),"counter");

        counter.tell(new AddMessage(20),ActorRef.noSender());

        Thread t1 = new Thread(() -> {

            for (int i = 1;i < 6; i++) {
                counter.tell(new AddMessage(i),ActorRef.noSender());
            }
        }

        );

        Thread t2 = new Thread(() -> {

            for (int i = 1;i < 6; i++) {
                counter.tell(new SubtractMessage(i),ActorRef.noSender());
            }
        }

        );

        t1.start();
        t2.start();
        try {

            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();

        }


        system.terminate();



    }
}
