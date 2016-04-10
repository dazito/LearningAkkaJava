package com.dazito.java.dakkabase;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.BalancingPool;
import akka.routing.RoundRobinGroup;
import akka.routing.RoundRobinPool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by daz on 25/02/2016.
 */
public class Main {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("dakkabase-java");
        ActorRef actorRef = system.actorOf(Props.create(DakkabaseDb.class), "dakkabase-db");

        // Create a routing actor pool with 8 child actors and round robin as the method to distribute the load across the actors
        // We can also pass a supervision startegy to the workerRouter to apply in the routees
        // .withSupervision(strategy) on the RoundRobinPool object
        ActorRef workerRouter = system.actorOf(Props.create(ArticleParserActor.class).withRouter(new RoundRobinPool(8)));

        // Simulate a list of actor paths
        Iterable<String> actors = new ArrayList<>();
        // Make a router from a list of actors - creating a router from a groups of actors
        ActorRef router = system.actorOf(new RoundRobinGroup(actors).props());

        // Broadcast a message to all the actors in the pool/group
        // router.tell(new Broadcast("Broadcasting message!"), ActorRef.noSender());

        // Dispatchers
        ActorRef actorWithDispatcher = system.actorOf(Props.create(DummyActorDispatchersExample.class).withDispatcher("custom-dispatcher"));

        System.out.println("Path:" + actorRef.path().name() + " | System:" + actorRef.path().root() + " | Path: " + actorRef.path().toString());

        List<ActorRef> routees = Arrays.asList(1, 2, 3, 4, 5)
                .stream()
                .map(x -> system.actorOf(Props.create(ArticleParser.class).withDispatcher("article-parsing-dispatcher")))
                .collect(Collectors.toList());
        Iterable<String> routeeAddresses = routees.stream().map(x -> x.path().toStringWithoutAddress()).collect(Collectors.toList());
        ActorRef workerRouterWithDispatcher = system.actorOf(new RoundRobinGroup(routeeAddresses).props());

        // Balancing Pool with a custom dispatcher
        ActorRef workerRouterBalancingPool = system.actorOf(new BalancingPool(8).props(Props.create(ArticleParser.class)), "balancing-pool-dispatcher");
    }
}
