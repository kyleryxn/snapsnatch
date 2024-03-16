package com.github.kyleryxn.snapsnatch.crawler.parse;

import com.github.kyleryxn.snapsnatch.crawler.WebParser;
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
public class RobotsTxtParser implements WebParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(RobotsTxtParser.class);

    private final Map<String, List<String>> robotsTxt;

    public RobotsTxtParser() {
        this.robotsTxt = new HashMap<>();
    }

    public Map<String, List<String>> getRobotsTxt() {
        return robotsTxt;
    }

    @Override
    public void parse(String content) {
        final String USER_AGENT_PREFIX = "user-agent:";
        final String DISALLOW_PREFIX = "disallow:";

        if (content == null || content.isBlank()) {
            LOGGER.warn("Content passed to parse method is empty.");
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
                    robotsTxt.putIfAbsent(userAgent, new ArrayList<>());
                } else if (line.toLowerCase().startsWith(DISALLOW_PREFIX)) {
                    String disallow = line.substring(DISALLOW_PREFIX.length()).trim();
                    robotsTxt.computeIfAbsent(userAgent, k -> new ArrayList<>()).add(disallow);
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

}