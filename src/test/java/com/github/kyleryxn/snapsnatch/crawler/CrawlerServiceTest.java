package com.github.kyleryxn.snapsnatch.crawler;

import com.github.kyleryxn.snapsnatch.BaseTest;
import com.github.kyleryxn.snapsnatch.image.model.Image;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@DisplayName("CrawlerService Test")
public class CrawlerServiceTest extends BaseTest {

    @Autowired
    private CrawlerService crawlerService;

    @Test
    void t1() {
        crawlerService.setBaseURL("https://transcendcosmetics.org/");
        crawlerService.crawl();

        ConcurrentMap<String, Set<Image>> images = new ConcurrentHashMap<>(crawlerService.getImages());
        Set<String> pics = images.entrySet().stream().flatMap(e -> e.getValue().stream()).map(Image::getURL).collect(Collectors.toSet());
        //pics.forEach(System.out::println);

        Set<Image> imagesData = images.entrySet().stream().flatMap(e -> e.getValue().stream()).collect(Collectors.toSet());
        imagesData.forEach(System.out::println);
    }

}
