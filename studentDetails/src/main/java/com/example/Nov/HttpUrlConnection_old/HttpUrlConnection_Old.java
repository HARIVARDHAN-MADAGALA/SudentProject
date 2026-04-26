package com.example.Nov.HttpUrlConnection_old;

import com.example.Nov.dto.AddressDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class HttpUrlConnection_Old {

//    @Value("${address.service.url}")
    private String baseUrl ="http://localhost:8082";

    private final ObjectMapper objectMapper;

    public HttpUrlConnection_Old(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public AddressDto getAdress(String name) {

        HttpURLConnection connection = null;

        try {
            URL url = new URL(baseUrl + "/address/name/" + name);

            connection = (HttpURLConnection) url.openConnection();

            // 🔹 Setup request
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            // 🔹 Call API
            int status = connection.getResponseCode();

            if (status != 200) {
                throw new RuntimeException("Failed with HTTP code: " + status);
            }

            // 🔹 Read response
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            // 🔹 Convert JSON → Object
            return objectMapper.readValue(response.toString(), AddressDto.class);

        } catch (Exception e) {
            throw new RuntimeException("Error calling Address Service", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}