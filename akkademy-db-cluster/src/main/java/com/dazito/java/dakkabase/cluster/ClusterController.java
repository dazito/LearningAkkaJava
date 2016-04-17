package com.dazito.java.dakkabase.cluster;

import akka.actor.AbstractActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;

/**
 * Created by daz on 13/04/2016.
 */
public class ClusterController extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(context().system(), this);

    final Cluster cluster = Cluster.get(getContext().system());

    @Override
    public void preStart() throws Exception {
        cluster.subscribe(self(), ClusterEvent.initialStateAsEvents(), ClusterEvent.MemberEvent.class, ClusterEvent.UnreachableMember.class);
    }

    @Override
    public void postStop() throws Exception {
        cluster.unsubscribe(self());
    }

    public ClusterController() {
        receive(ReceiveBuilder
                .match(ClusterEvent.MemberEvent.class, message -> log.info("\n\n******************** MemberEvent: {} *********************\n\n", message))
                .match(ClusterEvent.UnreachableMember.class, message -> log.info("\n\n******************* UnreachableMember: {} *******************\n\n", message))
                .build());
    }
}
