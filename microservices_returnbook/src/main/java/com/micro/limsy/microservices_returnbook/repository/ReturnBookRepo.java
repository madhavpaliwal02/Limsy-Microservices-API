package com.micro.limsy.microservices_returnbook.repository;

import com.micro.limsy.microservices_returnbook.model.ReturnBook;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReturnBookRepo extends JpaRepository<ReturnBook, Long> {

}
