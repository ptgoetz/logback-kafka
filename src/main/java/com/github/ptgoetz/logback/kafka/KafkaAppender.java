package com.github.ptgoetz.logback.kafka;

import java.util.Properties;

import org.slf4j.Marker;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import com.github.ptgoetz.logback.kafka.formatter.Formatter;
import com.github.ptgoetz.logback.kafka.formatter.MessageFormatter;

public class KafkaAppender extends AppenderBase<ILoggingEvent> {

    private String zookeeperHost;
    private Producer<String, String> producer;
    private Formatter formatter;
	private String brokerList;
	private String topic;
	
	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public void setBrokerList(String s) {
		this.brokerList = s;
	}
	
	public String getBrokerList() { return this.brokerList; }
	
    public String getZookeeperHost() {
        return zookeeperHost;
    }

    public void setZookeeperHost(String zookeeperHost) {
        this.zookeeperHost = zookeeperHost;
    }

    public Formatter getFormatter() {
        return formatter;
    }

    public void setFormatter(Formatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public void start() {
        if (this.formatter == null) {
            this.formatter = new MessageFormatter();
        }
        super.start();
        Properties props = new Properties();
        props.put("zk.connect", this.zookeeperHost);
        props.put("metadata.broker.list", this.brokerList);
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        ProducerConfig config = new ProducerConfig(props);
        this.producer = new Producer<String, String>(config);
    }

    @Override
    public void stop() {
        super.stop();
        this.producer.close();
    }

    @Override
    protected void append(ILoggingEvent event) {
    	Marker marker = event.getMarker();
        String payload = this.formatter.format(event);
        if (marker != null && marker.getName().startsWith("topic")) {
            this.producer.send(new KeyedMessage<String, String>(marker.getName(), payload));
        } else {
            this.producer.send(new KeyedMessage<String, String>(getTopic(), payload));
        }
    }

}
