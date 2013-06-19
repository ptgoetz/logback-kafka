package com.github.ptgoetz.logback.kafka.formatter;

import org.junit.Test;

import static junit.framework.Assert.*;
import junit.framework.TestCase;

public class JsonFormatterTest extends TestCase {

    @Test
    public void testJsonFormat() {
        String nonJsonMessage = "{\"level\":\"INFO\",\"logger\":\"test\",\"timestamp\":1370918376296,\"message\":\"foobar\"}";
        String jsonMessage = "{\"level\":\"INFO\",\"logger\":\"test\",\"timestamp\":1370918376296,\"message\":{\"foo\":\"bar\"}}";

        // non-JSON
        MockLoggingEvent event = new MockLoggingEvent(false);
        JsonFormatter formatter = new JsonFormatter();
        formatter.setExpectJson(false);
        String json = formatter.format(event);
        assertEquals(nonJsonMessage, json);

        // JSON
        event = new MockLoggingEvent(true);
        formatter.setExpectJson(true);
        json = formatter.format(event);
        assertEquals(jsonMessage, json);
    }
}
