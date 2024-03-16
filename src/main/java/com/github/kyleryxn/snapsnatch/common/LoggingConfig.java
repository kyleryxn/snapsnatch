package com.github.kyleryxn.snapsnatch.common;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import ch.qos.logback.core.util.FileSize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingConfig.class);
    private static final String ENCODER_PATTERN = "%d %green([%thread]) %highlight(%level) %logger{50} - %msg%n";

    public static void configure(Class<?> klass) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.reset();

        PatternLayoutEncoder consoleEncoder = new PatternLayoutEncoder();
        consoleEncoder.setContext(loggerContext);
        consoleEncoder.setPattern(ENCODER_PATTERN);
        consoleEncoder.start();

        PatternLayoutEncoder fileEncoder = new PatternLayoutEncoder();
        fileEncoder.setContext(loggerContext);
        fileEncoder.setPattern(ENCODER_PATTERN);
        fileEncoder.start();

        RollingFileAppender<ILoggingEvent> fileAppender = new RollingFileAppender<>();
        fileAppender.setContext(loggerContext);
        fileAppender.setName("FILE");
        fileAppender.setFile(getLogFilePath(klass));

        TimeBasedRollingPolicy<?> rollingPolicy = new TimeBasedRollingPolicy<>();
        rollingPolicy.setContext(loggerContext);
        rollingPolicy.setParent(fileAppender);
        rollingPolicy.setFileNamePattern(getLogFilePath(klass) + ".%d{yyyy-MM-dd}.log");
        rollingPolicy.setMaxHistory(30);
        rollingPolicy.setTotalSizeCap(FileSize.valueOf("10MB"));
        rollingPolicy.start();

        fileAppender.setEncoder(fileEncoder);
        fileAppender.setRollingPolicy(rollingPolicy);
        fileAppender.start();

        ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();
        consoleAppender.setContext(loggerContext);
        consoleAppender.setName("CONSOLE");
        consoleAppender.setEncoder(consoleEncoder);
        consoleAppender.start();

        ch.qos.logback.classic.Logger httpComponentsLogger = loggerContext.getLogger("org.apache.hc.client5");
        httpComponentsLogger.setLevel(Level.WARN);

        // Enable DEBUG level specifically for PoolingHttpClientConnectionManager
        ch.qos.logback.classic.Logger poolingManagerLogger = loggerContext.getLogger("org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager");
        poolingManagerLogger.setLevel(Level.DEBUG);

        ch.qos.logback.classic.Logger rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.INFO);
        rootLogger.addAppender(fileAppender);
        rootLogger.addAppender(consoleAppender);

        LOGGER.debug("Log configuration applied for log file: '{}'", getLogFilePath(klass));
    }

    private static String getLogFilePath(Class<?> klass) {
        String packageName = klass.getPackageName();
        String directory = packageName.replace("com.snapsnatch.", "").replace(".", "/");

        return "logs/" + directory + "/" + klass.getSimpleName() + ".log";
    }

}