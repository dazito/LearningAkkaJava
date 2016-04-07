package com.dazito.java.dakkabase;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Created by daz on 01/04/2016.
 */
public class DummyActorDispatchersExample extends AbstractActor {

    final Executor blockingIoDispatcher = context().system().dispatchers().lookup("blocking-io-dispatcher");

    public DummyActorDispatchersExample() {
        receive(ReceiveBuilder.matchAny(o -> {
                System.out.println("Message received");
                CompletableFuture.supplyAsync(this::sleep, blockingIoDispatcher)
                    .thenAccept(this::wokeUp);
            })
            .build());
    }


    private String sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "woke up!";
    }

    private void wokeUp(String message) {
        System.out.println("DummyActorDispatchersExample.accept - finished");
    }
}
