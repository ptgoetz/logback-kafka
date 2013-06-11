package com.github.ptgoetz.logback.kafka.formatter;

import java.util.Map;

import org.slf4j.Marker;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.LoggerContextVO;

public class MockLoggingEvent implements ILoggingEvent{
	private boolean jsonMessage = false;
	
	public MockLoggingEvent(boolean jsonMessage){
		this.jsonMessage = jsonMessage;
	}

	public Object[] getArgumentArray() {
		// TODO Auto-generated method stub
		return null;
	}

	public StackTraceElement[] getCallerData() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getFormattedMessage() {
		if(this.jsonMessage){
			return "{\"foo\":\"bar\"}";
		} else {
			return "foobar";
		}
	}

	public Level getLevel() {
		return Level.INFO;
	}

	public LoggerContextVO getLoggerContextVO() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLoggerName() {
		// TODO Auto-generated method stub
		return "test";
	}

	public Map<String, String> getMDCPropertyMap() {
		// TODO Auto-generated method stub
		return null;
	}

	public Marker getMarker() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, String> getMdc() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getThreadName() {
		// TODO Auto-generated method stub
		return null;
	}

	public IThrowableProxy getThrowableProxy() {
		// TODO Auto-generated method stub
		return null;
	}

	public long getTimeStamp() {
		// TODO Auto-generated method stub
		return 1370918376296L;
	}

	public boolean hasCallerData() {
		// TODO Auto-generated method stub
		return false;
	}

	public void prepareForDeferredProcessing() {
		// TODO Auto-generated method stub
		
	}

}
