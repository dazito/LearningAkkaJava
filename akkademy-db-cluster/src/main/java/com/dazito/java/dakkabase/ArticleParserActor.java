package com.dazito.java.dakkabase;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import com.dazito.java.dakkabase.messages.ParseArticle;

/**
 * Created by daz on 28/03/2016.
 */
public class ArticleParserActor extends AbstractActor {

    private ArticleParserActor() {
        receive(ReceiveBuilder
                .match(ParseArticle.class, msg -> sender().tell(ArticleParser.apply(msg.getHtmlBody()), self()))
                .build());
    }
}
