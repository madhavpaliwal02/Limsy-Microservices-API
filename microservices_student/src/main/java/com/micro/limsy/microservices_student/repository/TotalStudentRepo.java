package com.micro.limsy.microservices_student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.micro.limsy.microservices_student.model.TotalStudent;

public interface TotalStudentRepo extends JpaRepository<TotalStudent, Integer> {
    
}
