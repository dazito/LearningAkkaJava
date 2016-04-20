package com.dazito.java.dakkabase;

import akka.actor.AbstractActor;
import akka.actor.Status;
import akka.japi.pf.ReceiveBuilder;
import com.dazito.java.dakkabase.messages.ParseArticle;

/**
 * Created by daz on 28/03/2016.
 */
public class ArticleParserActor extends AbstractActor {

    private ArticleParserActor() {
        receive(ReceiveBuilder
                .match(ParseArticle.class, msg -> {
                    ArticleParser.apply(msg.getHtmlBody())
                            .onSuccess(body -> sender().tell(body, self()))
                            .onFailure(t -> sender().tell(new Status.Failure(t), self()));
                })
                .build());
    }
}
