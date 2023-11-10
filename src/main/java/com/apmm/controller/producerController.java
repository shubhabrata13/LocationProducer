package com.apmm.controller;

import com.apmm.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/kafka")
public final class producerController {
    @Autowired
    private ProducerService producerService;

    @GetMapping(path = "/health")
    public String health() {
        return "hello from producer";

    }

    @GetMapping("/produce")
    public Mono<String> getFile()
            throws URISyntaxException {
        return producerService.sendMessage();

    }

}