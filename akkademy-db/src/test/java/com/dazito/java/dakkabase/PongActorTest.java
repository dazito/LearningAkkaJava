package com.dazito.java.dakkabase;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.junit.Test;
import scala.concurrent.Future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static akka.pattern.Patterns.ask;
import static scala.compat.java8.FutureConverters.toJava;

/**
 * Created by daz on 21/02/2016.
 */
public class PongActorTest {

    private ActorSystem actorSystem = ActorSystem.create();
    private ActorRef actorRef = actorSystem.actorOf(Props.create(JavaPongActor.class));

    @Test
    public void shouldReplyToPingWithPong() throws Exception {
        Future scalaFuture = ask(actorRef, "Ping", 1000);

        // Convert from Scala to Java future
        final CompletionStage<String> completationStage = toJava(scalaFuture);
        final CompletableFuture<String> javaFuture = (CompletableFuture) completationStage;

        assert(javaFuture.get(1000, TimeUnit.MILLISECONDS).equals("Pong"));
    }

    @Test(expected = ExecutionException.class)
    public void shouldReplyToUnknownMessageWithFailure() throws Exception {
        Future scalaFuture = ask(actorRef, "unknown", 1000);
        final CompletionStage<String> completionStage = toJava(scalaFuture);
        final CompletableFuture<String> javaFuture = (CompletableFuture) completionStage;

        javaFuture.get(1000, TimeUnit.MILLISECONDS);
    }
}
