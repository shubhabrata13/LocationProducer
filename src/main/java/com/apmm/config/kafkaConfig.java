
package com.apmm.config;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class kafkaConfig {
	@Value("${spring.kafka.properties.bootstrap.servers}") 
	private String bootstrapServers;
	
	@Value("${spring.kafka.properties.sasl.mechanism}") 
	private String sasl_mechanism;
	
	@Value("${spring.kafka.properties.sasl.jaas.config}") 
	private String jaas_config;
	
	@Value("${spring.kafka.properties.security.protocol}") 
	private String security_protocol;
	

	
	  @Bean
	  public ProducerFactory<String, String> producerFactory() {
	    return new DefaultKafkaProducerFactory<>(
	        Map.of(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
	        	   ProducerConfig.RETRIES_CONFIG, 0,
	        	   ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432,
	        	   SaslConfigs.SASL_MECHANISM,sasl_mechanism,
	        	   SaslConfigs.SASL_JAAS_CONFIG,jaas_config,
	        	   CommonClientConfigs.SECURITY_PROTOCOL_CONFIG,security_protocol,
	        	   ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
	        	   ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class
	        ));
	  }
	  
	  @Bean
	  public KafkaTemplate<String, String> kafkaTemplate() {
	    return new KafkaTemplate<>(producerFactory());
	  }
	  
	  
	  public ConsumerFactory<String, String> consumerFactory() {
	        Map<String, Object> configProps = new HashMap<>();
	        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
	        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
	        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	        
	        return new DefaultKafkaConsumerFactory<>(configProps);
	    }

	    @Bean
	    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
	        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
	        factory.setConsumerFactory(consumerFactory());
	        
	        return factory;
	    }

}


