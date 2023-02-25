package com.micro.limsy.microservices_student.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.micro.limsy.microservices_student.dto.StudentRequest;
import com.micro.limsy.microservices_student.dto.StudentResponse;
import com.micro.limsy.microservices_student.service.StudentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentCtrl {

    private final StudentService studentService;

    /* Create a Student */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createStudent(@RequestBody StudentRequest studentRequest) {
        studentService.createStudent(studentRequest);
        return "Student Created Successfully...";
    }

    /* Get all Students */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StudentResponse> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public StudentResponse getStudent(@PathVariable("email") String email) {
        return studentService.getStudent(email);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public StudentResponse updateStudent(@RequestBody StudentRequest studentRequest) {
        return studentService.updateStudent(studentRequest);
    }

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteStudent(@PathVariable("email") String email) {
        studentService.deleteStudent(email);
        return "Student Deleted Sucessfully...";
    }

}
