package com.example.Nov.feign;


import com.example.Nov.dto.Adressdto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "address-service", url = "${address-service.url}", fallback = feignfallback.class)
public interface AddressFeignClient {


    @GetMapping("/address/name/{name}")
    Adressdto getAdress(@PathVariable String name);
}
