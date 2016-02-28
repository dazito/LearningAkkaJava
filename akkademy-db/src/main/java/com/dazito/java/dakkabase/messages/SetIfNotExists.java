package com.dazito.java.dakkabase.messages;

import java.io.Serializable;

/**
 * Created by daz on 27/02/2016.
 */
public class SetIfNotExists implements Serializable {

    private final String key;
    private final Object value;

    public SetIfNotExists(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }
}
