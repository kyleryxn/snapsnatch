package com.github.kyleryxn.snapsnatch.web;

import com.github.kyleryxn.snapsnatch.crawler.CrawlerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CrawlController {

    private final CrawlerService crawlerService;

    public CrawlController(CrawlerService crawlerService) {
        this.crawlerService = crawlerService;
    }

    @PostMapping("/crawl")
    public void startCrawling(@RequestParam String url) {
        crawlerService.setBaseURL(url);
        crawlerService.crawl();
    }

}
