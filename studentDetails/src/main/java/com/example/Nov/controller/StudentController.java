package com.example.Nov.controller;

import com.example.Nov.dto.Adressdto;
import com.example.Nov.dto.StudentDto;
import com.example.Nov.entity.Department;
import com.example.Nov.entity.Student;
import com.example.Nov.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {


    private final StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<Student> getAllStudent(){
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER')")
    public Student getStudent(@PathVariable Long id){
        return studentService.getStudent(id);
    }

    @GetMapping("/department/{id}")
    public Department getDepartmentByStudent(@PathVariable Long id){
        return studentService.getDepartmentByStudent(id);
    }


    @PostMapping
    public void addStudent(@RequestBody StudentDto studentDto){
        studentService.addStudent(studentDto);
    }

    @PostMapping("/department/{deptname}")
    public void addDepartment(@PathVariable String deptname){
        studentService.addDepartment(deptname);
    }

    @GetMapping("/address/{name}")
    public Adressdto getAdress(@PathVariable String name){

        return studentService.getAddressByname(name);
    }

}

