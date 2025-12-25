package com.example.Nov.feign;

import com.example.Nov.dto.Adressdto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
public class feignfallback implements AddressFeignClient {
    @Override
    public Adressdto getAdress(String name) {
        Adressdto adressdto = new Adressdto();

        adressdto.setName(name);
        adressdto.setAddress("Address not avaliable");

        return adressdto;
    }
}
