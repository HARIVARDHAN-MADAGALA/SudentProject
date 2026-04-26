package com.example.Nov.controller;

import com.example.Nov.dto.AddressDto;
import com.example.Nov.dto.StudentDto;
import com.example.Nov.entity.Department;
import com.example.Nov.entity.Student;
import com.example.Nov.repository.StudentRepository;
import com.example.Nov.service.StudentService;
import com.example.Nov.util.PerformanceTracker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final PerformanceTracker tracker;
    private final StudentRepository studentRepository;

    public StudentController(StudentService studentService, PerformanceTracker tracker,StudentRepository studentRepository){
        this.studentService = studentService;
        this.tracker = tracker;
        this.studentRepository = studentRepository;
    }

    // Calling all students
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<Student>> getAllStudent(){
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    // get student by student id
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<Student> getStudent(@PathVariable Long id){
        return ResponseEntity.ok(studentService.getStudent(id));
    }

    // get department by student name
    @GetMapping("/department/{name}")
    public ResponseEntity<Department> getDepartmentByStudent(@PathVariable String name){
        return ResponseEntity.ok(studentService.getDepartmentByStudent(name));
    }

    // inserting the students
    @PostMapping
    public ResponseEntity<String> addStudent(@RequestBody StudentDto studentDto){
        studentService.addStudent(studentDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Student created successfully");
    }

    // insert department
    @PostMapping("/department/{deptname}")
    public ResponseEntity<String> addDepartment(@PathVariable String deptname){
        studentService.addDepartment(deptname);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Department created successfully");
    }

    // get address by name from address service
    @GetMapping("/address/{name}")
    public ResponseEntity<AddressDto> getAdress(@PathVariable String name){
        return ResponseEntity.ok(studentService.getAddressByname(name));
    }

    // updating the department
    @PutMapping("/updateDept/{name}")
    public ResponseEntity<String> updateByname(@PathVariable String name,
                                               @RequestBody StudentDto studentDto){
        studentService.updateStudent(name, studentDto);
        return ResponseEntity.ok("Student updated successfully");
    }

    // delete the student
    @DeleteMapping("/name/{name}")
    public ResponseEntity<String> deleteByStudentName(@PathVariable String name){
        studentService.deleteByStudentname(name);
        return ResponseEntity.ok("Student deleted successfully");
    }



    ///  to demostrate thr database optimisation

    // ❌ BAD - triggers N+1 problem
    @GetMapping("/slow")
    public String slow() {
        List<Student> students = tracker.track("SLOW - N+1 Query",
                () -> {
            List<Student> studentss = studentRepository.findAll();
            studentss.forEach(s -> s.getDepartment().getDepname()); // triggers N+1 HERE
            return studentss;
    });
        // Force load department → triggers N+1
        return "Done - check console for time!";

    }

    // ✅ FIX 1 - JOIN FETCH  using INNER JOIN or JOIN
    @GetMapping("/fast-joinFetch")
    public String fastJoinFetch() {
        List<Student> students = tracker.track("FAST - JOIN FETCH",
                () ->  {
                    List<Student> studentss = studentRepository.findAllWithDepartment();
                    studentss.forEach(s -> s.getDepartment().getDepname()); // ✅ NO extra query — already fetched in 1 SQL
                    return studentss;
                }
        );
        return "Done - check console for time!";
    }

    // ✅ FIX 2 - EntityGraph uses LEFT JOIN
    @GetMapping("/fast-entityGraph")
    public String fastEntityGraph() {
        List<Student> students = tracker.track("FAST - EntityGraph",
                () ->  {
                    List<Student> studentss = studentRepository.findAllWithEntityGraph();
                    studentss.forEach(s -> s.getDepartment().getDepname()); // ✅ NO extra query — already fetched in 1 SQL
                    return studentss;
                }
        );
        return "Done - check console for time!";
    }

    // ✅ FIX 3 - Pagination
    @GetMapping("/paginated")
    public String paginated(@RequestParam(defaultValue = "0") int page) {
        Page<Student> result = tracker.track("FAST - Pagination (page " + page + ")",
                () -> studentRepository.findAll(PageRequest.of(page, 20))
        );
        return "Page " + page + " → " + result.getContent().size() + " students fetched";
    }
}