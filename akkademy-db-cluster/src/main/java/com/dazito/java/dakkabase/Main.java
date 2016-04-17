package com.dazito.java.dakkabase;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.dazito.java.dakkabase.cluster.ClusterController;

/**
 * Created by daz on 25/02/2016.
 */
public class Main {

    public static void main(String[] args) {
//        if(args.length == 0) {
//            ConfigFactory.parseString("akka.remote.netty.tcp.port=0");
//        }
//        else {
//            ConfigFactory.parseString("akka.remote.netty.tcp.port=" + args[0]);
//        }

        ActorSystem system = ActorSystem.create("dakkabase-java-cluster");
        ActorRef clusterController = system.actorOf(Props.create(ClusterController.class), "clusterController");
    }
}
