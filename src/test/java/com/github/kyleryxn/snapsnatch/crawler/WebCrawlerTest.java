package com.github.kyleryxn.snapsnatch.crawler;

import com.github.kyleryxn.snapsnatch.crawler.content.ContentParser;
import com.github.kyleryxn.snapsnatch.crawler.content.ParserService;
import com.github.kyleryxn.snapsnatch.crawler.http.WebContentReader;
import com.github.kyleryxn.snapsnatch.image.model.Image;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@SpringBootTest
public class WebCrawlerTest {

    @Autowired
    private WebContentReader webContentReader;

    @Autowired
    private ICrawlStateManager crawlStateManager;

    @Autowired
    private ContentParser HTMLContentParser;

    @Autowired
    private ContentParser robotsTxtContentParser;

    @Test
    void t1() {
        CrawlerService crawlerService = new CrawlerService(webContentReader, new ParserService(List.of(HTMLContentParser, robotsTxtContentParser)), crawlStateManager);
        crawlerService.setBaseURL("https://www.vulog.com/");
        crawlerService.crawl();

        ConcurrentMap<String, Set<Image>> images = new ConcurrentHashMap<>(crawlerService.getImages());
        Set<String> pics = images.entrySet().stream().flatMap(e -> e.getValue().stream()).map(Image::getURL).collect(Collectors.toSet());
        pics.forEach(System.out::println);
    }

}
