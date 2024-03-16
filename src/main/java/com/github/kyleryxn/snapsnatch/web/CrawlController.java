package com.github.kyleryxn.snapsnatch.web;

import com.github.kyleryxn.snapsnatch.crawler.WebCrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CrawlController {

    private final WebCrawlerService webCrawlerService;

    @Autowired
    public CrawlController(WebCrawlerService webCrawlerService) {
        this.webCrawlerService = webCrawlerService;
    }

    @PostMapping("/crawl")
    public void startCrawling(@RequestParam String url) {
        // Create WebCrawlerService instance with the provided URL
        webCrawlerService.setStartUrl(url);
        // Call the crawl method of WebCrawlerService
        webCrawlerService.crawl();
    }

}
