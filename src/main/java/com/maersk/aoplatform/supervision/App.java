package com.maersk.aoplatform.supervision;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * Created by g on 14/06/2017.
 */


class RestartException extends  Exception {


}

class ResumeException extends Exception {

}

class StopException extends  Exception {


}
public class App {
    public static void main(String[] args) throws Exception {
        final ActorSystem system = ActorSystem.create("supervision");
        final ActorRef parentActor = system.actorOf(Props.create(ParentActor.class),"parentActor");

        parentActor.tell("Increment", ActorRef.noSender());

        parentActor.tell("Increment", ActorRef.noSender());

        parentActor.tell("Resume", ActorRef.noSender());

        parentActor.tell("Increment", ActorRef.noSender());

        parentActor.tell("Restart", ActorRef.noSender());

        parentActor.tell("Increment", ActorRef.noSender());

        parentActor.tell("Increment", ActorRef.noSender());

        parentActor.tell("Stop", ActorRef.noSender());

        parentActor.tell("Increment", ActorRef.noSender());


        Thread.sleep(1000);

        system.terminate();

    }


}
