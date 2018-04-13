package com.dashuju.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import net.logstash.logback.encoder.LogstashEncoder;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;

/**
 * @author : LEVE TEAM
 * @date : Created in 2018/3/30 11:45
 */
@Configuration
public class Logstash {
    @Value("${logstash.shipper.host}")
    private String shipperHost;
    @Value("${logstash.shipper.port}")
    private Integer shipperPort;
    @Value("${logstash.level}")
    private String logLevel;
    @Value("${logstash.service.name}")
    private String serviceName;

    @PostConstruct
    public void startLogstashTcpSocketAppender() {
        Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

        LogstashTcpSocketAppender logstashTcpSocketAppender = new LogstashTcpSocketAppender();
        logstashTcpSocketAppender.setName("STASH");
        logstashTcpSocketAppender.addDestinations(new InetSocketAddress(shipperHost, shipperPort));

        LogstashEncoder encoder = new LogstashEncoder();
        // 设置自定义字段：服务名
        encoder.setCustomFields("{\"service\":\"" + serviceName + "\"}");
        encoder.start();

        logstashTcpSocketAppender.setEncoder(encoder);
        logstashTcpSocketAppender.setContext(rootLogger.getLoggerContext());
        logstashTcpSocketAppender.start();
        rootLogger.addAppender(logstashTcpSocketAppender);
        rootLogger.setLevel(Level.toLevel(logLevel));
    }
}
