package com.micro.limsy.microservices_student.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.micro.limsy.microservices_student.dto.StudentRequest;
import com.micro.limsy.microservices_student.dto.StudentResponse;
import com.micro.limsy.microservices_student.model.Student;
import com.micro.limsy.microservices_student.repository.StudentRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepo studentRepo;

    /* Create a student */
    public void createStudent(StudentRequest studentRequest) {
        Student student = maptoStudent(studentRequest);
        student.setStuId(UUID.randomUUID().toString());
        student.setDate(new Date());

        studentRepo.save(student);
    }

    /* Get all Students */
    public List<StudentResponse> getAllStudents() {
        List<Student> students = studentRepo.findAll();

        return students.stream().map(this::maptoStudentResponse).toList();
    }

    /* Get a Student */
    public StudentResponse getStudent(String studentId) {
        Student stu = studentRepo.findAll().stream().filter(student -> student.getStuId().equals(studentId))
                .findAny().get();

        if (stu == null)
            throw new EntityNotFoundException("Student not found...");

        return maptoStudentResponse(stu);
    }

    /* Update a Student */
    public StudentResponse updateStudent(StudentRequest studentRequest) {
        Student oldStudent = studentRepo.findAll().stream()
                .filter(stu -> stu.getEmail().equals(studentRequest.getEmail()))
                .findAny().get();

        if (oldStudent == null)
            throw new EntityNotFoundException("Student not found...");

        Student student = maptoStudent(studentRequest);
        student.setStuId(oldStudent.getStuId());
        student.setDate(oldStudent.getDate());

        studentRepo.delete(oldStudent);
        studentRepo.save(student);

        return maptoStudentResponse(student);
    }

    /* Delete a Student */
    public void deleteStudent(String studentId) {
        Student student = studentRepo.findAll().stream().filter(stu -> stu.getStuId().equals(studentId))
                .findAny().get();

        if (student == null)
            throw new EntityNotFoundException("Student not found...");

        studentRepo.delete(student);
    }

    private Student maptoStudent(StudentRequest studentRequest) {
        return Student.builder()
                .name(studentRequest.getName())
                .email(studentRequest.getEmail())
                .password(studentRequest.getPassword())
                .rollNo(studentRequest.getRollNo())
                .enrollment(studentRequest.getEnrollment())
                .course(studentRequest.getCourse())
                .semester(studentRequest.getSemester())
                .gender(studentRequest.getGender())
                .build();
    }

    private StudentResponse maptoStudentResponse(Student student) {
        return StudentResponse.builder()
                .stuId(student.getStuId())
                .name(student.getName())
                .email(student.getEmail())
                .password(student.getPassword())
                .rollNo(student.getRollNo())
                .enrollment(student.getEnrollment())
                .course(student.getCourse())
                .semester(student.getSemester())
                .gender(student.getGender())
                .date(student.getDate())
                .build();
    }

}
