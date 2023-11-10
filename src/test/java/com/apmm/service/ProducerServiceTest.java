package com.apmm.service;

import com.azure.storage.blob.BlobContainerAsyncClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.AssertionErrors;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import reactor.test.StepVerifier;

@Testcontainers
@RunWith(SpringRunner.class)
@DirtiesContext
@SpringBootTest(classes = {com.apmm.LocationProducer.LocationProducerApplication.class})
public class ProducerServiceTest {
    private static final String location_str = "{\n" +
            "  \"bdas\": [\n" +
            "    {\n" +
            "      \"name\": \"SIKOP\",\n" +
            "      \"type\": \"Business Defined Area\",\n" +
            "      \"bdaType\": \"POOL\",\n" +
            "      \"alternateCodes\": [\n" +
            "        {\n" +
            "          \"code\": \"SVOWWQGRTLKKA\",\n" +
            "          \"codeType\": \"locationId\"\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  ],\n" +
            "  \"name\": \"GODOVIC\",\n" +
            "  \"status\": \"InActive\",\n" +
            "  \"bdaType\": null,\n" +
            "  \"country\": {\n" +
            "    \"name\": \"Slovenia\",\n" +
            "    \"alternateCodes\": [\n" +
            "      {\n" +
            "        \"code\": \"3P1JF15ZCBG5A\",\n" +
            "        \"codeType\": \"locationId\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"code\": \"SI\",\n" +
            "        \"codeType\": \"localcode1\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"code\": \"140\",\n" +
            "        \"codeType\": \"localcode2\"\n" +
            "      }\n" +
            "    ]\n" +
            "  },\n" +
            "  \"geoType\": \"City\",\n" +
            "  \"parents\": [\n" +
            "    {\n" +
            "      \"name\": \"Slovenia\",\n" +
            "      \"type\": \"Country\",\n" +
            "      \"bdaType\": null,\n" +
            "      \"alternateCodes\": [\n" +
            "        {\n" +
            "          \"code\": \"3P1JF15ZCBG5A\",\n" +
            "          \"codeType\": \"locationId\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"code\": \"SI\",\n" +
            "          \"codeType\": \"localcode1\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"code\": \"140\",\n" +
            "          \"codeType\": \"localcode2\"\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  ],\n" +
            "  \"validTo\": \"2019-08-05\",\n" +
            "  \"hsudName\": null,\n" +
            "  \"latitude\": \"45.5724\",\n" +
            "  \"portFlag\": false,\n" +
            "  \"timeZone\": null,\n" +
            "  \"longitude\": \"10.0532\",\n" +
            "  \"validFrom\": \"1900-01-01\",\n" +
            "  \"locationId\": \"005JOMN2OKB5N\",\n" +
            "  \"restricted\": null,\n" +
            "  \"description\": null,\n" +
            "  \"dialingCode\": null,\n" +
            "  \"bdaLocations\": null,\n" +
            "  \"isMaerskCity\": true,\n" +
            "  \"olsonTimezone\": null,\n" +
            "  \"alternateCodes\": [\n" +
            "    {\n" +
            "      \"code\": \"G./\",\n" +
            "      \"codeType\": \"localcode2\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"code\": \"SIGOD\",\n" +
            "      \"codeType\": \"localcode1\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"code\": \"005JOMN2OKB5N\",\n" +
            "      \"codeType\": \"locationId\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"alternateNames\": null,\n" +
            "  \"subCityParents\": null,\n" +
            "  \"utcOffsetMinutes\": null,\n" +
            "  \"workaroundReason\": null,\n" +
            "  \"daylightSavingEnd\": null,\n" +
            "  \"daylightSavingTime\": null,\n" +
            "  \"daylightSavingStart\": null,\n" +
            "  \"postalCodeMandatory\": null,\n" +
            "  \"dialingCodeDescription\": null,\n" +
            "  \"stateProvinceMandatory\": null,\n" +
            "  \"daylightSavingShiftMinutes\": null\n" +
            "}";

    @Container
    static KafkaContainer kafkaContainer;

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.properties.bootstrap.servers", kafkaContainer::getBootstrapServers);
        registry.add("spring.kafka.properties.security.protocol", ()->{return "PLAINTEXT";});
    }

    @Autowired
    private ProducerService producerService;

    @Mock
    private BlobContainerAsyncClient blobContainerSourceClient;

    @After
    public void tearDown() {
        kafkaContainer.stop();
    }

    static {
        kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));
        kafkaContainer.start();
    }

    @Test
    public void it_should_send_updated_brand_event()  {
        Mono<String> data = producerService.sendMessage();
        assertNotNull(data);
        StepVerifier
                .create(data)
                .consumeNextWith(localData -> {
                    assertEquals("could send the data successfully",localData.toString());
                })
                .verifyComplete();
    }

}
