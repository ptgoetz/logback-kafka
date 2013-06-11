package com.github.ptgoetz.logback.kafka.formatter;

import ch.qos.logback.classic.spi.ILoggingEvent;

public interface Formatter {
	String format(ILoggingEvent event);
}
