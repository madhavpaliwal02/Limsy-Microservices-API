package com.micro.limsy.microservices_librarian.serviceImpl.service;

import java.util.List;

import com.micro.limsy.microservices_librarian.dto.IssuedBookResponse;
import com.micro.limsy.microservices_librarian.dto.LibrarianRequest;
import com.micro.limsy.microservices_librarian.dto.LibrarianResponse;

public interface LibrarianService {

    /* Create a Librarian */
    public void createLibrarian(LibrarianRequest librarianReq);

    /* Get all Librarians */
    public List<LibrarianResponse> getAllLibrarian();

    /* Get a Librarian */
    public LibrarianResponse getLibrarian(String librarianId);

    /* Update a Librarian */
    public LibrarianResponse updateLibrarian(LibrarianRequest librarianRequest, String librarianId);

    /* Delete a Librarian */
    public void deleteLibrarian(String librarianId);

    /* Additional Functions */

    /* Get All IssuedBook for a Librarian */
    public List<IssuedBookResponse> getIssuedBooks_Librarian(String librarianId);

    /* Get count of Librarians */
    public long getCount();
}
