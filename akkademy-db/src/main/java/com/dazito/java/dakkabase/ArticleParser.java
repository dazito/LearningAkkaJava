package com.dazito.java.dakkabase;


import com.jasongoodwin.monads.Try;
import de.l3s.boilerpipe.extractors.ArticleExtractor;

/**
 * Created by daz on 28/03/2016.
 */
public class ArticleParser {

    public static Try<String> apply(String html) {
        return Try.ofFailable(() ->  ArticleExtractor.INSTANCE.getText(html));
    }
}
