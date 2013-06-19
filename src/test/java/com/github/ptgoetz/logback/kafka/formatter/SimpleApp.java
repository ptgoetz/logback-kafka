package com.github.ptgoetz.logback.kafka.formatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleApp {
    public static final Logger LOG = LoggerFactory.getLogger(SimpleApp.class);

    public static void main(String[] args) {
        LOG.info("Hello {}", "World!");
    }
}
