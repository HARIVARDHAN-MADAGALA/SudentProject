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

//    Calling all students
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<Student> getAllStudent(){
        return studentService.getAllStudents();
    }

//    get student by student id
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER')")
    public Student getStudent(@PathVariable Long id){
        return studentService.getStudent(id);
    }

//    get department by student id
    @GetMapping("/department/{name}")
    public Department getDepartmentByStudent(@PathVariable String name){
        return studentService.getDepartmentByStudent(name);
    }


//    inserting the students
    @PostMapping
    public void addStudent(@RequestBody StudentDto studentDto){
        studentService.addStudent(studentDto);
    }

//    insert deptname
    @PostMapping("/department/{deptname}")
    public void addDepartment(@PathVariable String deptname){
        studentService.addDepartment(deptname);
    }

//    get address by name form addres service
    @GetMapping("/address/{name}")
    public Adressdto getAdress(@PathVariable String name){

        return studentService.getAddressByname(name);
    }

    // updating the department
    @PutMapping("/updateDept/{name}")
    public void updateByname(@PathVariable String name, @RequestBody StudentDto studentDto){


        studentService.updateStudent(name, studentDto);
    }

    // delete the student
    @DeleteMapping("/name/{name}")
    public void deleteByStudentName(@PathVariable String name){
         studentService.deleteByStudentname(name);
    }
}

