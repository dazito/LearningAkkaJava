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

    @Test
    public void printToConsole() throws Exception {
        askPong("Ping").thenAccept(s -> System.out.println("printToConsole - Replied with: " + s));
        Thread.sleep(100);
    }

    @Test
    public void shouldCauseFailure() {
        askPong("causeError").handle((x, throwable) -> {
            if(throwable != null) {
                System.out.println("shouldCauseFailure - Error: " + throwable);
            }
            return null;
        });
    }

    @Test
    public void shouldCauseFailureAndRecover() {
        System.out.println("shouldCauseFailureAndRecover");
        CompletionStage<String> completionStage = askPong("causeError").exceptionally(throwable -> {
            return "default"; // In case of error, return "default"
        });
    }

    @Test
    public void recoverFromFailureAsync() {
        askPong("causeError")
                .handle((result, exception) -> {
                    if (exception == null) return CompletableFuture.completedFuture(result);
                    else return askPong("Ping");
                }).thenCompose(x -> x);
    }

    @Test
    public void composingFutures() {
        // Compose futures and check for errors at the end of the pipeline
        askPong("ping")
                .thenCompose(x -> askPong("ping" + x))
                .handle((message, throwable) -> {
                    if(throwable != null) return "default";
                    else return message;
                });
    }

    @Test
    public void combiningFutures() {
        askPong("ping")
                .thenCombine(askPong("ping"), (s, s2) -> s + s2); // "PongPong"
    }

    public CompletionStage<String> askPong(String message) {
        Future scalaFuture = ask(actorRef, message, 1000);
        CompletionStage<String> completionStage = toJava(scalaFuture);
        return completionStage;
    }
}
