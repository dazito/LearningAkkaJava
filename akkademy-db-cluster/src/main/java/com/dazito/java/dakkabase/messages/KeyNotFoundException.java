package com.dazito.java.dakkabase.messages;

import java.io.Serializable;

/**
 * Created by daz on 25/02/2016.
 */
public class KeyNotFoundException extends Exception implements Serializable {
    public final String key;

    public KeyNotFoundException(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
