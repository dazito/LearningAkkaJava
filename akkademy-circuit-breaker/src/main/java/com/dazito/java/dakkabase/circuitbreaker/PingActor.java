package com.dazito.java.dakkabase.circuitbreaker;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.actor.Status;
import akka.japi.pf.ReceiveBuilder;

/**
 * Created by daz on 01/05/2016.
 */
public class PingActor extends AbstractActor {

    public static Props props() {
        return Props.create(PingActor.class);
    }

    public PingActor() {
        receive(ReceiveBuilder.match(String.class, message -> {
            Thread.sleep(100);
            sender().tell(new Status.Success("Ok"), self());
        })
        .build());
    }
}
