package com.example.adress.controller;

import com.example.adress.dto.AddressDto;
import com.example.adress.entity.StudentAdress;
import com.example.adress.service.AdressSevice;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class adressController {


    private AdressSevice adressSevice;

    public adressController(AdressSevice adressSevice){
        this.adressSevice = adressSevice;
    }

    // get all address
    @GetMapping("/all")
    public List<StudentAdress> getAddress(){
        return adressSevice.getStudentAdress();
    }

    //get address by name
    @GetMapping("/name/{name}")
    public AddressDto getAddressByname(@PathVariable String name){
        return adressSevice.getAddess(name);
    }

    @PostMapping("/add")
    public void addAddress(@RequestBody AddressDto addressDto){

        adressSevice.addAddress(addressDto);
    }
}
