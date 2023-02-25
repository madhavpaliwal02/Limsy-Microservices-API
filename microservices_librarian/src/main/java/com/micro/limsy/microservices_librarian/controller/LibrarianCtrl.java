package com.micro.limsy.microservices_librarian.controller;

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

import com.micro.limsy.microservices_librarian.dto.LibrarianRequest;
import com.micro.limsy.microservices_librarian.dto.LibrarianResponse;
import com.micro.limsy.microservices_librarian.service.LibrarianService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/librarian")
@RequiredArgsConstructor
public class LibrarianCtrl {

    private final LibrarianService librarianService;

    /* Create a Librarian */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createLibrarian(@RequestBody LibrarianRequest librarianRequest) {
        librarianService.createLibrarian(librarianRequest);
        return "Librarian Created Successfully...";
    }

    /* Get all Librarians */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LibrarianResponse> getAllLibrarian() {
        return librarianService.getAllLibrarian();
    }

    /* Get a Librarian */
    @GetMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public LibrarianResponse getLibrarian(@PathVariable("email") String email) {
        return librarianService.getLibrarian(email);
    }

    /* Update a Librarian */
    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public LibrarianResponse updateLibrarian(@RequestBody LibrarianRequest librarianRequest) {
        return librarianService.updateLibrarian(librarianRequest);
    }

    /* Delete a Librarian */
    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteLibrarian(@PathVariable("email") String email) {
        librarianService.deleteLibrarian(email);
        return "Librarian Deleted Successfully...";
    }

}
