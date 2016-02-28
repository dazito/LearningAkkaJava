package com.dazito.java.dakkabase.messages;

import java.io.Serializable;

/**
 * Created by daz on 27/02/2016.
 */
public class DeleteMessage implements Serializable {

    private final String key;

    public DeleteMessage(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
