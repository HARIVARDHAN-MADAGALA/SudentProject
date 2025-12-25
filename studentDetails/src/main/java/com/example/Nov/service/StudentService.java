package com.example.Nov.service;

import com.example.Nov.dto.Adressdto;
import com.example.Nov.dto.StudentDto;
import com.example.Nov.entity.Department;
import com.example.Nov.entity.Student;
import com.example.Nov.exception.ResourceNotFoundException;
import com.example.Nov.feign.AddressFeignClient;
import com.example.Nov.repository.DepartmentRepository;
import com.example.Nov.repository.StudentRepository;
import com.example.Nov.restconfig.RestClient;
import com.example.Nov.webclientConfig.WebClientclass;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class StudentService {


    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;
    private static final Logger log =
            LoggerFactory.getLogger(StudentService.class);
    //  for resttemplate
//    private final RestClient restClient;
//
//    //Getting
//    public StudentService(StudentRepository studentRepository, DepartmentRepository departmentRepository, RestClient restClient){
//        this.studentRepository= studentRepository;
//        this.departmentRepository = departmentRepository;
//        this.restClient = restClient;
//    }

//    for WebClient
//    private final WebClientclass webClientclass;
//
//    public StudentService(StudentRepository studentRepository, DepartmentRepository departmentRepository, WebClientclass webClientclass){
//        this.studentRepository= studentRepository;
//        this.departmentRepository = departmentRepository;
//        this.webClientclass = webClientclass;
//    }

    private final AddressFeignClient addressFeignClient;

    public StudentService(StudentRepository studentRepository, DepartmentRepository departmentRepository, AddressFeignClient addressFeignClient){
        this.studentRepository= studentRepository;
        this.departmentRepository = departmentRepository;
        this.addressFeignClient = addressFeignClient;
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public Student getStudent(Long id){

            log.info("Request received to fetch student with id {}" + id);

        return studentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Student not found" + id));
    }

    public Department getDepartmentByStudent(Long id){
        return studentRepository.findById(id).get().getDepartment();
    }

    //post

    public void addStudent(StudentDto studentDto){
        Student student = new Student();
        student.setStdname(studentDto.name);
        Department department = departmentRepository.findById(studentDto.departmentid).get();
        student.setDepartment(department);
        studentRepository.save(student);
    }

    public void addDepartment(String deptname){
        Department department = new Department();
        department.setDepname(deptname);
        departmentRepository.save(department);
    }

    public Adressdto getAddressByname(String name){

//        return restClient.getAdress(name);
//        return webClientclass.getAdress(name);
          return addressFeignClient.getAdress(name);


    }

}


