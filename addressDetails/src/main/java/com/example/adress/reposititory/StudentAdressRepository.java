package com.example.adress.reposititory;


import com.example.adress.entity.StudentAdress;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentAdressRepository extends MongoRepository<StudentAdress,String> {


    StudentAdress findByName(String name);
}
