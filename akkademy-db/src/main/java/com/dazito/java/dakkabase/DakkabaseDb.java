package com.dazito.java.dakkabase;

import akka.actor.AbstractActor;
import akka.actor.Status;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import com.dazito.java.dakkabase.messages.GetRequest;
import com.dazito.java.dakkabase.messages.KeyNotFoundException;
import com.dazito.java.dakkabase.messages.SetRequest;
import com.dazito.java.dakkabase.messages.UnknownMessageException;

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

                    // Let the send know it succeed
                    sender().tell(new Status.Success(message.getKey()), self());
                })
                .match(GetRequest.class, message -> {
                    log.info("Received Get Request: {}", message);
                    Object value = map.get(message.getKey());
                    Object response = (value != null)
                            ? value
                            : new Status.Failure(new KeyNotFoundException(message.getKey()));

                    // Reply back with the value
                    sender().tell(response, self());
                })
                .matchAny(o -> {
                    log.info("Received unknown message {}", o);

                    // Let the send know it failed
                    sender().tell(new Status.Failure(new UnknownMessageException()), self());
                })
                .build());
    }
}
