package com.micro.limsy.microservices_book.controller;

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

import com.micro.limsy.microservices_book.dto.BookRequest;
import com.micro.limsy.microservices_book.dto.BookResponse;
import com.micro.limsy.microservices_book.serviceImpl.service.BookService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookCtrl {

    private final BookService bookService;

    /* Create a Book */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createBook(@RequestBody BookRequest bookRequest) {
        bookService.createBook(bookRequest);
        return "Book Created Successfully...";
    }

    /* Get all Books */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookResponse> getAllBooks() {
        return bookService.getAllBooks();
    }

    /* Get a Book */
    @GetMapping("/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public BookResponse getBook(@PathVariable("bookId") String bookId) {
        return bookService.getBook(bookId);
    }

    /* Update a Book */
    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public BookResponse updateBook(@RequestBody BookRequest bookRequest) {
        return bookService.updateBook(bookRequest);
    }

    /* Delete a Book */
    @DeleteMapping("/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteBook(@PathVariable("bookId") String bookId) {
        bookService.deleteBook(bookId);
        return "Book Delete Successfully...";
    }

    /************************** Additional Functions **************************/

    /* Get IssuedBooks for Student */
    @GetMapping("/student-ib/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public List<BookResponse> getIssuedBook_Student(@PathVariable("studentId") String studentId) {
        return this.bookService.getIssuedBook_Student(studentId);
    }
}
