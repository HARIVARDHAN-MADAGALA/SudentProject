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
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

//    get all students
    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

//    get student by student id
    public Student getStudent(Long id){

            log.info("Request received to fetch student with id {}" + id);

        return studentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Student not found" + id));
    }

//    get department by student name
    public Department getDepartmentByStudent(String name){
        Student student = studentRepository.findByStdname(name).orElseThrow(() -> new ResourceNotFoundException(" Student is not Exists with id " + name));
        return student.getDepartment();
    }

    // inserting student by checking department name if present
    public void addStudent(StudentDto studentDto){
            Student student = new Student();
            student.setStdname(studentDto.getName());
        Department department = departmentRepository.findByDepname(studentDto.getDepartmentName()).orElseThrow(() -> new ResourceNotFoundException("Department is not found for id" + studentDto.getDepartmentName()));
        student.setDepartment(department);
        studentRepository.save(student);
    }

//    inserting department
    public void addDepartment(String deptname){
        Department department = new Department();
        department.setDepname(deptname);
        departmentRepository.save(department);
    }

    //    get address by name form addres service
    public Adressdto getAddressByname(String name){

//        return restClient.getAdress(name);
//        return webClientclass.getAdress(name);
          return addressFeignClient.getAdress(name);


    }

    // update the dept
    public void updateStudent (String name, StudentDto studentDto){

        Student student = studentRepository.findByStdname(name).orElseThrow(() ->new RuntimeException("Student not found"));

        Department department = departmentRepository.findByDepname(studentDto.getDepartmentName()).orElseThrow(()->new RuntimeException("DEpt not found"));

            student.setDepartment(department);

            studentRepository.save(student);
    }

    // Delete the student by name
    @Transactional
    public void deleteByStudentname(String name){

        Optional<Student> student = studentRepository.findByStdname(name);

        if (student.isPresent()){
            studentRepository.deleteByStdname(name);
        }
        else {
            throw new RuntimeException("Student not found with name: " + name);
        }

    }
}


