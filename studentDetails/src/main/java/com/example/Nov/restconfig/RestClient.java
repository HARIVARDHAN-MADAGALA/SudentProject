package com.example.Nov.restconfig;

import com.example.Nov.dto.Adressdto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestClient {

    private final RestTemplate restTemplate;

    public RestClient(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }


    public Adressdto getAdress(String name) {

        String url = "http://localhost:8082/address/name/" + name;

        return restTemplate.getForObject(url, Adressdto.class);
    }
}
