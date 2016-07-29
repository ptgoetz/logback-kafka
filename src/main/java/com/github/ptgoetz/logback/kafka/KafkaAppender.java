package com.github.ptgoetz.logback.kafka;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.github.ptgoetz.logback.kafka.formatter.Formatter;
import com.github.ptgoetz.logback.kafka.formatter.MessageFormatter;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.util.Properties;

public class KafkaAppender extends AppenderBase<ILoggingEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaAppender.class);
    private Formatter formatter = new MessageFormatter();
    private boolean logToSystemOut = false;
    private String kafkaProducerProperties;
    private String topic;
    private KafkaProducer producer;

    @Override
    public void start() {
        super.start();
        LOGGER.info("Starting KafkaAppender...");
        final Properties properties = new Properties();
        try {
            properties.load(new StringReader(kafkaProducerProperties));
            producer = new KafkaProducer<>(properties);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        if (topic == null) {
            LOGGER.error("KafkaAppender requires a topic. Add this to the appender configuration.");
            System.out.println("KafkaAppender requires a topic. Add this to the appender configuration.");
        } else {
            LOGGER.info("KafkaAppender will publish messages to the '{}' topic.",topic);
            System.out.println("KafkaAppender will publish messages to the '" + topic + "' topic.");
        }
        LOGGER.info("kafkaProducerProperties = {}", kafkaProducerProperties);
        LOGGER.info("Kafka Producer Properties = {}", properties);
        if (logToSystemOut) {
            System.out.println("KafkaAppender: kafkaProducerProperties = '" + kafkaProducerProperties + "'.");
            System.out.println("KafkaAppender: properties = '" + properties + "'.");
        }
    }

    @Override
    public void stop() {
        super.stop();
        LOGGER.info("Stopping KafkaAppender...");
        this.producer.close();
    }

    @Override
    protected void append(ILoggingEvent event) {
        String string = this.formatter.format(event);
        if (logToSystemOut) {
            System.out.println("KafkaAppender: Appending string: '" + string + "'.");
        }
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, string);
        this.producer.send(producerRecord);
    }

    public Formatter getFormatter() {
        return formatter;
    }

    public void setFormatter(Formatter formatter) {
        this.formatter = formatter;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getLogToSystemOut() {
        return logToSystemOut + "";
    }

    public void setLogToSystemOut(String logToSystemOutString) {
        if ("true".equalsIgnoreCase(logToSystemOutString)) {
            this.logToSystemOut = true;
        }
    }

    public String getKafkaProducerProperties() {
        return kafkaProducerProperties;
    }

    public void setKafkaProducerProperties(String kafkaProducerProperties) {
        this.kafkaProducerProperties = kafkaProducerProperties;
    }
}