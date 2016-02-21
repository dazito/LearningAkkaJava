package com.dazito.java.dakkabase;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import com.dazito.java.dakkabase.messages.SetRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by daz on 20/02/2016.
 */
public class DakkabaseDb extends AbstractActor {

    /**
     * Protected to enable access from test cases
     */
    protected final LoggingAdapter log = Logging.getLogger(context().system(), this);
    protected final Map<String, Object> map = new HashMap<>();

    private DakkabaseDb() {
        receive(ReceiveBuilder
                .match(SetRequest.class, message -> {
                        log.info("Received Set Request - key: {} | value {}", message.getKey(), message.getValue());
                        map.put(message.getKey(), message.getValue());
                    }
                )
                .matchAny(o -> log.info("Received unknown message {}", o))
                .build());
    }
}
