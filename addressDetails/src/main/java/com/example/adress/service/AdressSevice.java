package com.example.adress.service;


import com.example.adress.dto.AddressDto;
import com.example.adress.entity.StudentAdress;
import com.example.adress.reposititory.StudentAdressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdressSevice {


    private final StudentAdressRepository studentAdressRepository;

    public AdressSevice(StudentAdressRepository studentAdressRepository){
        this.studentAdressRepository = studentAdressRepository;
    }

//    get all address
    public List<StudentAdress> getStudentAdress(){

        return studentAdressRepository.findAll();
    }

    // get the address from student name
    public AddressDto getAddess(String name) {

        StudentAdress entity = studentAdressRepository.findByName(name);
        AddressDto AddressDto = new AddressDto();

        AddressDto.setId(entity.getId());
        AddressDto.setName(entity.getName());
        AddressDto.setAddress(entity.getAddress());

        return AddressDto;
    }

    // insert the address

    public void addAddress(AddressDto addressDto){

        StudentAdress studentAdress = new StudentAdress();
        studentAdress.setName(addressDto.getName());
        studentAdress.setAddress(addressDto.getAddress());

        studentAdressRepository.save(studentAdress);
    }
}
