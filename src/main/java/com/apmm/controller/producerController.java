package com.apmm.controller;

import com.apmm.service.AzureBlobService;
import com.apmm.service.ProducerService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;

@RestController
@RequestMapping("/kafka")
public final class producerController {
    @Autowired
    private ProducerService producerService;

    @Autowired
    private AzureBlobService azureBlobAdapter;

      @GetMapping(path = "/health")
    public String health() {
        return "hello from producer";

    }

    @GetMapping("/produce")
    public ResponseEntity<byte[]> getFile
            ()
            throws URISyntaxException {
        List<String> data = azureBlobAdapter
                .listBlobs();
        byte[] resource = null;
        for (String element : data){
             resource = azureBlobAdapter
                            .getFile(element);
             String message = new String(resource, Charset.forName("ISO-8859-1"));
            producerService.sendMessage(message);
        }
        return ResponseEntity.ok().contentType(MediaType
                        .APPLICATION_OCTET_STREAM)
                .body(resource);

    }

}