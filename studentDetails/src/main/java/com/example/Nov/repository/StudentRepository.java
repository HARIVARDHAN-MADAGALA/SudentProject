package com.example.Nov.repository;

import com.example.Nov.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    Optional<Student> findByStdname(String stdname);

    void deleteByStdname(String stdname);
}
