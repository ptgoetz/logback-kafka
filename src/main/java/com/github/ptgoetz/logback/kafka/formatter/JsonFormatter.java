package com.github.ptgoetz.logback.kafka.formatter;

import ch.qos.logback.classic.spi.ILoggingEvent;

public class JsonFormatter implements Formatter {
    private static final String QUOTE = "\"";
    private static final String COLON = ":";
    private static final String COMMA = ",";

    private boolean expectJson = false;
    private boolean includeMethodAndLineNumber = false;

    public String format(ILoggingEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        fieldName("level", sb);
        quote(event.getLevel().levelStr, sb);
        sb.append(COMMA);
        fieldName("logger", sb);
        quote(event.getLoggerName(), sb);
        sb.append(COMMA);
        fieldName("timestamp", sb);
        sb.append(event.getTimeStamp());
        sb.append(COMMA);
        fieldName("message", sb);
        if (this.expectJson) {
            sb.append(event.getFormattedMessage());
        } else {
            quote(event.getFormattedMessage(), sb);
        }
        if(includeMethodAndLineNumber) {
            sb.append(COMMA);
            // Caller Data
            StackTraceElement[] callerDataArray = event.getCallerData();
            if (callerDataArray != null && callerDataArray.length > 0) {
                StackTraceElement stackTraceElement = callerDataArray[0];
                fieldName("method", sb);
                quote(stackTraceElement.getMethodName(), sb);
                sb.append(COMMA);
                fieldName("lineNumber", sb);
                quote(stackTraceElement.getLineNumber() + "", sb);
            }
        }
        sb.append("}");
        return sb.toString();
    }

    private static void fieldName(String name, StringBuilder sb) {
        quote(name, sb);
        sb.append(COLON);
    }

    private static void quote(String value, StringBuilder sb) {
        sb.append(QUOTE);
        sb.append(value);
        sb.append(QUOTE);
    }

    public boolean getExpectJson() {
        return expectJson;
    }

    public void setExpectJson(boolean expectJson) {
        this.expectJson = expectJson;
    }

    public boolean getIncludeMethodAndLineNumber() {
        return includeMethodAndLineNumber;
    }

    public void setIncludeMethodAndLineNumber(boolean includeMethodAndLineNumber) {
        this.includeMethodAndLineNumber = includeMethodAndLineNumber;
    }
}