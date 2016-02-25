package com.dazito.java.dakkabase;

import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * Created by daz on 25/02/2016.
 */
public class Main {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("dakkabase-java");
        system.actorOf(Props.create(DakkabaseDb.class), "dakkabase-db");
    }
}
