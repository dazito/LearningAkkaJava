package com.dazito.java.dakkabase.messages;

import java.io.Serializable;

/**
 * Created by daz on 25/02/2016.
 */
public class GetRequest implements Serializable {
    private final String key;

    public GetRequest(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
