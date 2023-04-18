package com.micro.limsy.microservices_book.serviceImpl.service;

import java.util.List;

import com.micro.limsy.microservices_book.dto.BookRequest;
import com.micro.limsy.microservices_book.dto.BookResponse;

public interface BookService {
    
    /* Create a Book */
    public void createBook(BookRequest bookRequest);
    
    /* Get all Books */
    public List<BookResponse> getAllBooks();
    
    /* Get a Book */
    public BookResponse getBook(String bookId);
    
    /* Update a Book */
    public BookResponse updateBook(BookRequest bookRequest);
    
    /* Delete a Book */
    public void deleteBook(String bookId);

    /************************** Additional Functions **************************/
    /* Get IssuedBooks for Student */
    public List<BookResponse> getIssuedBook_Student(String studentId);

    /* Get Count for books available */
    public long getCount();
}
