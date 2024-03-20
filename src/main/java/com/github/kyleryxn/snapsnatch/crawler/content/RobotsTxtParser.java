package com.github.kyleryxn.snapsnatch.crawler.content;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
class RobotsTxtParser implements Parser {
    private static final Logger LOGGER = LoggerFactory.getLogger(RobotsTxtParser.class);
    private final Map<String, List<String>> directives;

    RobotsTxtParser() {
        this.directives = new HashMap<>();
    }

    public Map<String, List<String>> getDirectives() {
        return directives;
    }

    @Override
    public String getContentType() {
        return Content.ROBOTS.getContentType();
    }

    @Override
    public void parse(String content) {
        final String USER_AGENT_PREFIX = "user-agent:";
        final String DISALLOW_PREFIX = "disallow:";

        if (content == null || content.isBlank()) {
            LOGGER.warn("Content passed to util method is empty.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new StringReader(content))) {
            String line;
            String userAgent = "";

            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                if (line.toLowerCase().startsWith(USER_AGENT_PREFIX)) {
                    userAgent = line.substring(USER_AGENT_PREFIX.length()).trim();
                    directives.putIfAbsent(userAgent, new ArrayList<>());
                } else if (line.toLowerCase().startsWith(DISALLOW_PREFIX)) {
                    String disallow = line.substring(DISALLOW_PREFIX.length()).trim();
                    directives.computeIfAbsent(userAgent, k -> new ArrayList<>()).add(disallow);
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

}