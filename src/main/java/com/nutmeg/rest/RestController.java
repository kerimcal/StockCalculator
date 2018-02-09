package com.nutmeg.rest;

import com.nutmeg.model.Holding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/v1/restcontroller")
public class RestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestController.class);

    @GetMapping(path="/test-get", produces = APPLICATION_JSON_VALUE)
    public String kickOff() {
        return "test-get invoked";
    }

    @PostMapping(path = "/test-post", produces = MediaType.APPLICATION_JSON_VALUE)
    public Holding reserve(@RequestBody @Valid Holding request,
                            @RequestHeader final HttpHeaders headers) throws Exception {
        LOGGER.info("=========================INCOMING test-post REQUEST =====================\n" );
        return request;
    }
}
