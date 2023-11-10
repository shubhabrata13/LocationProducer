package com.apmm.config;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.kafka.sender.SenderOptions;

import java.util.Map;

@Configuration
public class ReactiveKafkaProducerConfig {

    @Value("${spring.kafka.properties.bootstrap.servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.properties.sasl.mechanism}")
    private String sasl_mechanism;

    @Value("${spring.kafka.properties.sasl.jaas.config}")
    private String jaas_config;

    @Value("${spring.kafka.properties.security.protocol}")
    private String security_protocol;

    @Bean
    public ReactiveKafkaProducerTemplate<String, String> reactiveKafkaProducerTemplate(
            KafkaProperties properties) {

        return new ReactiveKafkaProducerTemplate<String, String>(SenderOptions.create(Map.of(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                ProducerConfig.RETRIES_CONFIG, 0,
                ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432,
                SaslConfigs.SASL_MECHANISM, sasl_mechanism,
                SaslConfigs.SASL_JAAS_CONFIG, jaas_config,
                CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, security_protocol,
                //CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "PLAINTEXT",
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class

        )));
    }
}

