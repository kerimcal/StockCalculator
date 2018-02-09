package com.nutmeg.rest.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.POST;

@Component
public class RequestForwarder {

    private final RestTemplate restTemplate;

    @Autowired
    public RequestForwarder(final RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public <T> T postForEntity(final String url,
                               final HttpEntity<?> request,
                               final Class<T> type) {
        return restTemplate.postForEntity(url, request, type).getBody();
    }

    public ResponseEntity postRequestWithHeaders(final String url,
                                                 final Object requestBody,
                                                 final Class<?> responseType,
                                                 final HttpHeaders headers) {
        return postRequest(url, requestBody, headers, responseType);
    }

    public ResponseEntity postRequest(final String url, final Object requestBody, final Class<?> responseType){
        final HttpHeaders headers = buildHeaders(MediaType.APPLICATION_JSON);
        return postRequest(url, requestBody, headers, responseType);
    }

    public ResponseEntity getRequest(final String url, final Class<?> responseType){
        return restTemplate.getForEntity(url, responseType);
    }

    public HttpHeaders buildHeaders(final MediaType contentType){
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(contentType);
        return headers;
    }

    private ResponseEntity postRequest(final String url, final Object requestBody, final HttpHeaders headers, final Class<?> responseType){
        final HttpEntity<? extends Object> requestEntity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(url, POST, requestEntity, responseType);
    }
}
