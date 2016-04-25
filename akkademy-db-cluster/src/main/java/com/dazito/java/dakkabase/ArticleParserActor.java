package com.dazito.java.dakkabase;

import akka.actor.AbstractActor;
import akka.actor.Status;
import akka.japi.pf.ReceiveBuilder;

/**
 * Created by daz on 28/03/2016.
 */
public class ArticleParserActor extends AbstractActor {

    private ArticleParserActor() {
        receive(ReceiveBuilder
                .match(String.class, msg -> {
                    ArticleParser.apply(msg)
                            .onSuccess(body -> sender().tell(body, self()))
                            .onFailure(t -> sender().tell(new Status.Failure(t), self()));
                })
                .build());
    }
}
