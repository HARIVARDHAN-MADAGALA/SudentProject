package com.example.Nov.feign;

import com.example.Nov.dto.AddressDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
public class feignfallback implements AddressFeignClient {
    @Override
    public AddressDto getAdress(String name) {
        AddressDto AddressDto = new AddressDto();

        AddressDto.setName(name);
        AddressDto.setAddress("Address not avaliable");

        return AddressDto;
    }
}
