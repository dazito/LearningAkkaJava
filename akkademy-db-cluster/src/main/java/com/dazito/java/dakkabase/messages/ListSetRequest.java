package com.dazito.java.dakkabase.messages;

import java.io.Serializable;
import java.util.List;

/**
 * Created by daz on 29/02/2016.
 */
public class ListSetRequest implements Serializable {
    private final List<SetRequest> list;

    public ListSetRequest(List<SetRequest> list) {
        this.list = list;
    }

    public List<SetRequest> getList() {
        return list;
    }
}
