package com.github.kyleryxn.snapsnatch.crawler;

import com.github.kyleryxn.snapsnatch.crawler.content.ImageExtractor;
import com.github.kyleryxn.snapsnatch.crawler.content.LinkExtractor;
import com.github.kyleryxn.snapsnatch.crawler.content.HTMLParser;
import com.github.kyleryxn.snapsnatch.crawler.content.RobotsTxtParser;
import com.github.kyleryxn.snapsnatch.image.model.Image;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class WebCrawlerTest {

    @Test
    void t1() {
        WebContentReader webContentReader = new WebContentReader(new HttpClientFactory().createCustom());
        WebCrawlerService webCrawlerService = new WebCrawlerService(webContentReader, new RobotsTxtParser(), new HTMLParser(new ImageExtractor(), new LinkExtractor()));
        webCrawlerService.setStartUrl("https://spring.io/");
        webCrawlerService.crawl();

        ConcurrentMap<String, Set<Image>> images = new ConcurrentHashMap<>(webCrawlerService.getImages());
        ConcurrentMap<String, Boolean> visited = new ConcurrentHashMap<>(webCrawlerService.getVisited());
        Set<String> pics = images.entrySet().stream().flatMap(e -> e.getValue().stream()).map(Image::getURL).collect(Collectors.toSet());
        pics.forEach(System.out::println);
    }

}
