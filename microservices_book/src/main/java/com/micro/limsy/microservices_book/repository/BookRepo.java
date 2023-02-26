package com.micro.limsy.microservices_book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.micro.limsy.microservices_book.model.Book;

public interface BookRepo extends JpaRepository<Book, Long> {

}
