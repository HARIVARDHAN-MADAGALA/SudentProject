package com.example.Nov.repository;

import com.example.Nov.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    Optional<Student> findByStdname(String stdname);

    void deleteByStdname(String stdname);

    // Fix 1 - JOIN FETCH (solves N+1)
    @Query("SELECT s FROM Student s JOIN FETCH s.department")
    List<Student> findAllWithDepartment();

    // Fix 2 - EntityGraph (renamed!)
    @EntityGraph(attributePaths = {"department"})
    @Query("SELECT s FROM Student s")
    List<Student> findAllWithEntityGraph();  // ← separate name!

    // Fix 3 - Pagination
    Page<Student> findAll(Pageable pageable);
}
