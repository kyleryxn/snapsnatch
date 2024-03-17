package com.github.kyleryxn.snapsnatch.crawler.util;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
class SimpleURLComparator implements URLComparator {

    @Override
    public boolean isDuplicate(Set<String> urls, String newUrl) {
        for (String url : urls) {
            if (url.equals(newUrl) || url.equals(newUrl + "/") || newUrl.equals(url + "/")) {
                return true;
            }
        }

        return false;
    }

}
