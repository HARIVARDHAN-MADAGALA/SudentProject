package com.example.Nov.repository;

import com.example.Nov.entity.Department;
import com.example.Nov.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long> {

    Optional<Department> findByDepname(String depname);

}
