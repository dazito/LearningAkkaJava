package com.dazito.java.dakkabase;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

/**
 * Created by daz on 01/04/2016.
 */
public class DummyActorDispatchersExample extends AbstractActor {

    public DummyActorDispatchersExample() {
        receive(ReceiveBuilder.matchAny(o -> System.out.println("Message received")).build());
    }
}
