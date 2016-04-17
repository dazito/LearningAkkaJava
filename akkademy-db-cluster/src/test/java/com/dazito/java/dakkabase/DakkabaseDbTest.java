package com.dazito.java.dakkabase;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import com.dazito.java.dakkabase.messages.SetRequest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by daz on 20/02/2016.
 */
public class DakkabaseDbTest {

    private ActorSystem actorSystem = ActorSystem.create();

    @Test
    public void itShouldPlaceKeyValueFromSetMessageIntoMap() {
        TestActorRef<DakkabaseDb> actorRef = TestActorRef.create(actorSystem, Props.create(DakkabaseDb.class));

        actorRef.tell(new SetRequest("key", "value"), ActorRef.noSender());
        DakkabaseDb dakkabaseDb = actorRef.underlyingActor();

        assertEquals(dakkabaseDb.map.get("key"), "value");
    }
}
