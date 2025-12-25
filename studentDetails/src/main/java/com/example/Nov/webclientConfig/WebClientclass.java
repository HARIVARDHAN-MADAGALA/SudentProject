package com.example.Nov.webclientConfig;

import com.example.Nov.dto.Adressdto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebClientclass {

    private final WebClient webClient;

    public WebClientclass(WebClient webClient){
        this.webClient = webClient;
    }

    public Adressdto getAdress(String name) {

        return webClient.get()
                .uri("/address/name/{name}",name)
                .retrieve()
                .bodyToMono(Adressdto.class)
                .block();
    }
}
