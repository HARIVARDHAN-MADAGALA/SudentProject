package com.example.Nov.httpClientConfig.non_blocking;

import com.example.Nov.dto.AddressDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

@Component
public class HttpClientConfig_nonBlocking {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public HttpClientConfig_nonBlocking(ObjectMapper objectMapper) {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = objectMapper;
    }

    public CompletableFuture<AddressDto> getAdress(String name) {

        String url = "http://localhost:8082/address/name/" + name;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        return httpClient
                .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() != 200) {
                        throw new RuntimeException("HTTP error: " + response.statusCode());
                    }

                    try {
                        return objectMapper.readValue(response.body(), AddressDto.class);
                    } catch (Exception e) {
                        throw new RuntimeException("JSON parsing failed", e);
                    }
                });
    }
}