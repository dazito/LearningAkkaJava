package com.dazito.java.dakkabase.messages;

import java.io.Serializable;

/**
 * Created by daz on 28/03/2016.
 */
public class ParseArticle implements Serializable {
    private final String htmlBody;

    public ParseArticle(String htmlBody) {
        this.htmlBody = htmlBody;
    }

    public String getHtmlBody() {
        return htmlBody;
    }
}
