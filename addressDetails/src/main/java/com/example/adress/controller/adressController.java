package com.example.adress.controller;

import com.example.adress.dto.AdressDto;
import com.example.adress.service.AdressSevice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class adressController {


    private AdressSevice adressSevice;

    public adressController(AdressSevice adressSevice){
        this.adressSevice = adressSevice;
    }

    @GetMapping("/id/{id}")
    public AdressDto getAddress(@PathVariable String id){
        return adressSevice.getStudentAdress(id);
    }

    @GetMapping("/name/{name}")
    public AdressDto getAddressByname(@PathVariable String name){
        return adressSevice.getAddess(name);
    }
}
