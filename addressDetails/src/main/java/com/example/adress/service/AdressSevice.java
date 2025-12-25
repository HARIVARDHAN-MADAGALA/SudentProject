package com.example.adress.service;


import com.example.adress.dto.AdressDto;
import com.example.adress.entity.StudentAdress;
import com.example.adress.reposititory.StudentAdressRepository;
import org.springframework.stereotype.Service;

@Service
public class AdressSevice {


    private final StudentAdressRepository studentAdressRepository;

    public AdressSevice(StudentAdressRepository studentAdressRepository){
        this.studentAdressRepository = studentAdressRepository;
    }

    public AdressDto getStudentAdress(String id){

        AdressDto adressDto = new AdressDto();
        adressDto.setId(id);
        adressDto.setName(studentAdressRepository.findById(id).get().getName());
        adressDto.setAddress(studentAdressRepository.findById(id).get().getAddress());

        return adressDto;
    }

    public AdressDto getAddess(String name) {

        StudentAdress entity = studentAdressRepository.findByName(name);
        AdressDto adressDto = new AdressDto();

        adressDto.setId(entity.getId());
        adressDto.setName(entity.getName());
        adressDto.setAddress(entity.getAddress());

        return adressDto;
    }
}
