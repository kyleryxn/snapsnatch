package com.github.kyleryxn.snapsnatch.crawler;

import com.github.kyleryxn.snapsnatch.crawler.extract.ImageExtractor;
import com.github.kyleryxn.snapsnatch.crawler.extract.LinkExtractor;
import com.github.kyleryxn.snapsnatch.crawler.parse.HTMLParser;
import com.github.kyleryxn.snapsnatch.crawler.parse.RobotsTxtParser;
import com.github.kyleryxn.snapsnatch.http.HttpClientFactory;
import com.github.kyleryxn.snapsnatch.model.Image;
import com.github.kyleryxn.snapsnatch.service.WebCrawlerService;
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
        Set<String> pics = images.entrySet().stream().flatMap(e -> e.getValue().stream()).map(Image::getUrl).collect(Collectors.toSet());
        pics.forEach(System.out::println);
    }

}
