package com.apmm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;

@Service
public final class ProducerService {
	private static final Logger logger = LoggerFactory.getLogger(ProducerService.class);

	@Autowired
	private final KafkaTemplate<String, String> kafkaTemplate;

	/*@Autowired
	private LocationRepository ld;*/

	//private final KafkaTemplate<String, String> kafkaTemplate;
	private final String TOPIC = "locationref.topic.internal.any.v1";

	public ProducerService(KafkaTemplate<String, String> kafkaProducerTemplate) {
		this.kafkaTemplate = kafkaProducerTemplate;
	}

	public String sendMessage(String message) {
		logger.info(String.format("$$$$ => Producing message: %s", message));
		String uniqueID = UUID.randomUUID().toString();
		this.kafkaTemplate.send(TOPIC, uniqueID,message);

		/*ListenableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(TOPIC, uniqueID,message);
		future.addCallback(new ListenableFutureCallback<>() {
			@Override
			public void onFailure(Throwable ex) {
				logger.info("Unable to send message=[ {} ] due to : {}", message, ex.getMessage());
			}

			@Override
			public void onSuccess(SendResult<String, String> result) {
				logger.info("Sent message=[ {} ] with offset=[ {} ]", message, result.getRecordMetadata().offset());
			}
		});*/
		
		return uniqueID ;
	}

	/*public Mono<String> produceAllMessage() {
		ld.findAll()
				.map(item->sendMessage(item))
				.doOnError(throwable -> logger.error("throwable: {0}", throwable))
				.doOnComplete(() -> logger.info("Completed sending: {}"))
				.then();
		return Mono.just("Success");
	}*/

	/*public void sendMessage(String message) {
		logger.info(String.format("$$$$ => Producing message: %s", message));
		reactiveKafkaProducerTemplate.send(TOPIC,UUID.randomUUID().toString(), message)
				.doOnSuccess(senderResult -> logger.info("sent {} offset : {}", message, senderResult.recordMetadata().offset()))
				.subscribe();
	}*/
}