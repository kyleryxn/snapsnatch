package com.github.kyleryxn.snapsnatch.crawler.util;

import java.util.Set;

public interface URLComparator {

    boolean isDuplicate(Set<String> urls, String newUrl);

}
