package com.micro.limsy.microservices_issuedbook.serviceImpl.service;

import java.util.List;

import com.micro.limsy.microservices_issuedbook.dto.IssuedBookRequest;
import com.micro.limsy.microservices_issuedbook.dto.IssuedBookResponse;
import com.micro.limsy.microservices_issuedbook.model.IssuedBook;

public interface IssuedBookService {

    /* Issued a Book */
    public void issueBook(IssuedBookRequest issuedBookRequest);

    /* Get all IssuedBooks */
    public List<IssuedBookResponse> getAllIssuedBooks();

    /* Get a IssuedBook */
    public IssuedBookResponse getIssuedBook(String ibookId);

    /* Delete a IssuedBook */
    public void deleteIssuedBook(String ibookId);

    /* Get all IssuedBook Objects */
    public List<IssuedBook> getAllIssueBooks();

    /* Get a IssuedBook Objects */
    public IssuedBook getAllIssueBooks(String ibookId);

    /* Get ALL IssuedBooks for a Librarian */
    public List<IssuedBookResponse> getIssuedBooks_Librarian(String librarianId);
}
