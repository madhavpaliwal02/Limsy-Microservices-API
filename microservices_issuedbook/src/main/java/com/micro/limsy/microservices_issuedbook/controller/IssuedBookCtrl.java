package com.micro.limsy.microservices_issuedbook.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.micro.limsy.microservices_issuedbook.dto.IssuedBookRequest;
import com.micro.limsy.microservices_issuedbook.dto.IssuedBookResponse;
import com.micro.limsy.microservices_issuedbook.service.IssuedBookService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/issuedbook")
@RequiredArgsConstructor
public class IssuedBookCtrl {

    private final IssuedBookService issuedBookService;

    /* Issue a Book */
    @PostMapping
    public String issueBook(@RequestBody IssuedBookRequest issuedBookRequest) {
        issuedBookService.issueBook(issuedBookRequest);
        return "Book Issued successfully...";
    }

    /* Get all IssuedBook */
    @GetMapping
    public List<IssuedBookResponse> getAllIssuedBooks() {
        return issuedBookService.getAllIssuedBooks();
    }

    /* Get a IssuedBook */
    @GetMapping("/{ibookId}")
    public IssuedBookResponse getIssuedBook(@PathVariable("ibookId") String ibookId){
        return issuedBookService.getIssuedBook(ibookId);
    }









}
