package com.github.kyleryxn.snapsnatch.util;

import com.github.kyleryxn.snapsnatch.crawler.content.PageContent;

public interface ContentReader {

    PageContent readContent(String directory);

}