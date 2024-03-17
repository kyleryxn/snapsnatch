package com.github.kyleryxn.snapsnatch.crawler;

import com.github.kyleryxn.snapsnatch.crawler.content.ElementExtractor;
import com.github.kyleryxn.snapsnatch.crawler.content.LinkExtractor;
import com.github.kyleryxn.snapsnatch.crawler.content.HTMLParser;
import com.github.kyleryxn.snapsnatch.crawler.content.RobotsTxtParser;
import com.github.kyleryxn.snapsnatch.image.model.Image;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@SpringBootTest
public class WebCrawlerTest {

    @Autowired
    private ElementExtractor imageExtractor;

    @Test
    void t1() {
        WebContentReader webContentReader = new WebContentReader(new HttpClientFactory().createCustom());
        CrawlerService crawlerService = new CrawlerService(webContentReader, new RobotsTxtParser(), new HTMLParser(imageExtractor, new LinkExtractor()));
        crawlerService.setStartUrl("https://spring.io/");
        crawlerService.crawl();

        ConcurrentMap<String, Set<Image>> images = new ConcurrentHashMap<>(crawlerService.getImages());
        ConcurrentMap<String, Boolean> visited = new ConcurrentHashMap<>(crawlerService.getVisited());
        Set<String> pics = images.entrySet().stream().flatMap(e -> e.getValue().stream()).map(Image::getURL).collect(Collectors.toSet());
        pics.forEach(System.out::println);
    }

}
