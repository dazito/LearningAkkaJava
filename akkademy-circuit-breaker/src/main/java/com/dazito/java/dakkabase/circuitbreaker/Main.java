package com.dazito.java.dakkabase.circuitbreaker;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.CircuitBreaker;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.compat.java8.FutureConverters;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;

import java.util.concurrent.TimeUnit;

/**
 * Created by daz on 01/05/2016.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        ActorSystem system = ActorSystem.create("circuit-breaker-actor-system");
        ActorRef pingActor = system.actorOf(PingActor.props(), "pingActor");

        CircuitBreaker circuitBreaker = new CircuitBreaker(
                system.scheduler(),
                3,
                FiniteDuration.create(1, TimeUnit.SECONDS),
                FiniteDuration.create(1, TimeUnit.SECONDS),
                system.dispatcher());

        circuitBreaker.onClose(Main::onClose);
        circuitBreaker.onHalfOpen(Main::onHalfOpen);
        circuitBreaker.onOpen(Main::onOpen);

        Timeout timeout = Timeout.apply(2000);

        Await.result(Patterns.ask(pingActor, "Ping", timeout), timeout.duration());

        for(int i = 0; i < 99999999; i++) {
            Future future = circuitBreaker.callWithCircuitBreaker(() -> Patterns.ask(pingActor, "Ping!", timeout));

            FutureConverters.toJava(future).handle((o,throwable) -> {
                if(throwable != null) {
                    System.out.println("Received a value from the actor! - Value: " + o);
                }
                else {
                    System.out.println("Error: " + throwable.toString());
                }

                return null;
            });

            Thread.sleep(50);
        }

    }

    private static void onClose() {
        System.out.println("Circuit breaks on close!");
    }

    private static void onHalfOpen() {
        System.out.println("Circuit breaks on half close!");
    }

    private static void onOpen() {
        System.out.println("Circuit breaks on open!");
    }
}
