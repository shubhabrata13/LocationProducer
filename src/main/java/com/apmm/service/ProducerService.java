package com.apmm.service;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobContainerAsyncClient;
import com.azure.storage.blob.models.BlobListDetails;
import com.azure.storage.blob.models.ListBlobsOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public final class ProducerService {
	private static final Logger logger = LoggerFactory.getLogger(ProducerService.class);

	@Autowired
	private BlobContainerAsyncClient blobContainerSourceClient;

	@Autowired
	private ReactiveKafkaProducerTemplate<String, String> reactiveKafkaProducerTemplate;

	private final String TOPIC = "location.poc";

	public ProducerService(ReactiveKafkaProducerTemplate<String, String> reactiveKafkaProducerTemplate,BlobContainerAsyncClient blobContainerSourceClient) {
		this.reactiveKafkaProducerTemplate = reactiveKafkaProducerTemplate;
		this.blobContainerSourceClient=blobContainerSourceClient;
	}

	public ProducerService() {
		super();
	}

	public Mono<String> sendMessage() {

		return blobContainerSourceClient.listBlobs(new ListBlobsOptions()
						.setMaxResultsPerPage(2)
						.setDetails(new BlobListDetails().setRetrieveDeletedBlobs(false)))
				.flatMap(blobItem->blobContainerSourceClient
						.getBlobAsyncClient(blobItem.getName()).downloadContent())
				.flatMap(data -> reactiveKafkaProducerTemplate.send(TOPIC,UUID.randomUUID().toString(),data.toString()))
				.then(Mono.just("could send the data successfully"));

	}
}