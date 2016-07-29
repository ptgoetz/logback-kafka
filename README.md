# logback-kafka


Logback appenders for logging data to Apache Kafka


## Maven Dependency
To use logback-kafka in your project add to following to your pom.xml:

```xml
<dependency>
    <groupId>com.github.ptgoetz</groupId>
    <artifactId>logback-kafka</artifactId>
    <version>0.2.0</version>
</dependency>
```

## Configuration

To configure your application to log to Kafka, add an appender entry in 
your logback configuration file, a Kafka topic name to log to, 
and specify your Kafka Producer properties. At a minimum, you must 
provide the 'bootstrap.servers' property. The properties you submit 
will be passed on to the Kafka Producer. A complete guide to the Producer 
properties can be found [here](https://kafka.apache.org/documentation.html#producerconfigs).

An option to log to System out is provided as a sanity check while setting up.

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<configuration>
    <appender name="KAFKA"
        class="com.github.ptgoetz.logback.kafka.KafkaAppender">
        <topic>mytopic</topic>
        <kafkaProducerProperties>
            bootstrap.servers=127.0.0.1:9092
            acks=all
        </kafkaProducerProperties>
        <logToSystemOut>true</logToSystemOut>
    </appender>
    <root level="debug">
        <appender-ref ref="KAFKA" />
    </root>
</configuration>
```

## Overriding Default Behavior
By default, the Kafka appender will simply write the received log 
message to the kafka queue. You can override this behavior by 
specifying a custom formatter class:

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<configuration>
    <appender name="KAFKA"
        class="com.github.ptgoetz.logback.kafka.KafkaAppender">
        <topic>foo</topic>
        <!-- Any configuration property defined here
        https://kafka.apache.org/documentation.html#producerconfigs
        will be passed through to the Kafka Producer: -->
        <kafkaProducerProperties>
            bootstrap.servers=127.0.0.1:9092
            acks=all
        </kafkaProducerProperties>
        <!-- specify a custom formatter -->
        <formatter class="com.github.ptgoetz.logback.kafka.formatter.JsonFormatter">
            <!--
            Whether we expect the log message to be JSON encoded or not.
            If set to "false", the log message will be treated as a string,
            and wrapped in quotes. Otherwise it will be treated as a parseable
            JSON object.
            -->
            <expectJson>true</expectJson>
            <!-- optional -->
            <includeMethodAndLineNumber>true</includeMethodAndLineNumber>
        </formatter>
    </appender>
    <root level="debug">
        <appender-ref ref="KAFKA" />
    </root>
</configuration>
```

Formatters simply need to implement the `com.github.ptgoetz.logback.kafka.formatter.Formatter` interface:

```java
package com.github.ptgoetz.logback.kafka.formatter;

import ch.qos.logback.classic.spi.ILoggingEvent;

public interface Formatter {
    String format(ILoggingEvent event);
}
```

You can find the `ch.qos.logback.classic.spi.ILoggingEvent` javadoc [here](http://logback.qos.ch/apidocs/ch/qos/logback/classic/spi/ILoggingEvent.html).


