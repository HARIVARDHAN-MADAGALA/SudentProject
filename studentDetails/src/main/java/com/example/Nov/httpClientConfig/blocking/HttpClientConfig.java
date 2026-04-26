package com.example.Nov.httpClientConfig.blocking;

import com.example.Nov.dto.AddressDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class HttpClientConfig {

    private final HttpClient httpClient;

    public HttpClientConfig() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public AddressDto getAdress(String name) {

        try {
            String url = "http://localhost:8082/address/name/" + name;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();

            // 🔹 Convert JSON → Object
            return objectMapper.readValue(response.body(), AddressDto.class);

        } catch (Exception e) {
            throw new RuntimeException();
        }


    }
}


//👉 ObjectMapper is required when you use Java 11 HttpClient.You didn’t write it with RestTemplate because Spring was doing it behind your back.
//
//
//HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//here  .send is checked exception so we need to write code in try catch method
