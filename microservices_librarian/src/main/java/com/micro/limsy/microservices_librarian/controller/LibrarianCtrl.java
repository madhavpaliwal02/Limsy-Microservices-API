package com.micro.limsy.microservices_librarian.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.micro.limsy.microservices_librarian.dto.LibrarianRequest;
import com.micro.limsy.microservices_librarian.dto.LibrarianResponse;
import com.micro.limsy.microservices_librarian.service.LibrarianService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/librarian")
@RequiredArgsConstructor
public class LibrarianCtrl {

    private final LibrarianService librarianService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createLibrarian(@RequestBody LibrarianRequest librarianRequest) {
        librarianService.createLibrarian(librarianRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LibrarianResponse> getAllLibrarian(){
        return librarianService.getAllLibrarian();
    }

    @GetMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public LibrarianResponse getLibrarian(@PathVariable("email") String email){
        return librarianService.getLibrarian(email);
    }
}
