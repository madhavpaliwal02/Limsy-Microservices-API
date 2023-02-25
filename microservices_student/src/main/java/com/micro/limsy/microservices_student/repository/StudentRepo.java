package com.micro.limsy.microservices_student.repository;

import com.micro.limsy.microservices_student.model.Student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student, Long> {

}
