package com.github.kyleryxn.snapsnatch.crawler.content;

public interface Parser {

    String getContentType();

    void parse(String content);

}