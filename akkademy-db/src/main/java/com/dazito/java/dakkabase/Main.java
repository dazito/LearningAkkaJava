package com.dazito.java.dakkabase;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * Created by daz on 25/02/2016.
 */
public class Main {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("dakkabase-java");
        ActorRef actorRef = system.actorOf(Props.create(DakkabaseDb.class), "dakkabase-db");

        System.out.println("Path:" + actorRef.path().name() + " | System:" + actorRef.path().root() + " | Path: " + actorRef.path().toString());
    }
}
