package com.micro.limsy.microservices_issuedbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.micro.limsy.microservices_issuedbook.model.IssuedBook;

public interface IssuedBookRepo extends JpaRepository<IssuedBook, Long> {
    
}
