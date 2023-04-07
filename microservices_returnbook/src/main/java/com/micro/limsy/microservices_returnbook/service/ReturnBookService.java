package com.micro.limsy.microservices_returnbook.service;

import com.micro.limsy.microservices_returnbook.dto.ReturnBookResponse;
import com.micro.limsy.microservices_returnbook.model.Book;
import com.micro.limsy.microservices_returnbook.model.IssuedBook;
import com.micro.limsy.microservices_returnbook.model.Librarian;
import com.micro.limsy.microservices_returnbook.model.ReturnBook;
import com.micro.limsy.microservices_returnbook.model.Student;
import com.micro.limsy.microservices_returnbook.repository.ReturnBookRepo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class ReturnBookService {

    private final ReturnBookRepo returnBookRepo;

    private final RestTemplate restTemplate;

    /* Return a Book */
    public void returnBook(String iBookId) {

        String url = "http://localhost:8104/api/issuedbook/ib/" + iBookId;

        // Get the IssuedBook from IssuedBook Service
        IssuedBook ibook = restTemplate.getForObject(url, IssuedBook.class);

        // If record not found, then
        if (ibook == null)
            throw new EntityNotFoundException("IssuedBook not found...");

        // We need to delete that record from IssuedBook
        restTemplate.delete("http://localhost:8104/api/issuedbook/" + ibook.getIBookId());

        // And added it in a returnbook object
        ReturnBook returnBook = mapToReturnBook(ibook);
        returnBook.setRbookId(UUID.randomUUID().toString());
        returnBook.setDate(new Date());
        returnBookRepo.save(returnBook);
    }

    /* Get All ReturnBooks */
    public List<ReturnBookResponse> getAllReturnBooks() {
        List<ReturnBookResponse> returnBookResponseList = new ArrayList<>();

        returnBookRepo.findAll().stream().map(rbook -> returnBookResponseList.add(mapToReturnBookResponse(rbook)));

        return returnBookResponseList;
    }

    /* Get a ReturnBook */
    public ReturnBookResponse getReturnBook(String rBookId) {
        ReturnBook rBook = returnBookRepo.findAll().stream().filter(rbook -> rbook.getRbookId().equals(rBookId))
                .findAny().get();

        if (rBook == null)
            throw new EntityNotFoundException("ReturnBook Not Found");
        return mapToReturnBookResponse(rBook);
    }

    /* Get All ReturnedBooks */
    public List<ReturnBook> getAllReturnedBooks() {
        return returnBookRepo.findAll();
    }

    /* Delete a ReturnBook */
    public void deleteReturnBook(String rBookId) {
        ReturnBook rBook = returnBookRepo.findAll().stream().filter(rbook -> rbook.getRbookId().equals(rBookId))
                .findAny().get();

        if (rBook == null)
            throw new EntityNotFoundException("ReturnBook Not Found...");
        returnBookRepo.delete(rBook);
    }

    /* Mapping Function : ReturnBookReq -> ReturnBook */
    private ReturnBook mapToReturnBook(IssuedBook iBook) {
        return ReturnBook.builder()
                .studentId(iBook.getStudentId())
                .librarianId(iBook.getLibrarianId())
                .bookId(iBook.getBookId())
                .build();
    }

    /* Mapping Function : Librarian + Student + Book -> ReturnBook */
    private ReturnBookResponse mapToReturnBookResponse(ReturnBook rbook) {

        Librarian lib = restTemplate.getForObject("http://librarian-service/api/librarian/" + rbook.getLibrarianId(),
                Librarian.class);
        Student stu = restTemplate.getForObject("http://student-service/api/student/" + rbook.getStudentId(),
                Student.class);
        Book book = restTemplate.getForObject("http://book-service/api/book/" + rbook.getBookId(),
                Book.class);

        ReturnBookResponse rBookResponse = ReturnBookResponse.builder()
                // returnBook Details
                .rBookId(rbook.getRbookId())
                .date(rbook.getDate())
                // Book Details
                .title(book.getTitle())
                .authorName(book.getAuthorName())
                .edition(book.getEdition())
                // Student Details
                .sname(stu.getName())
                .rollNo(stu.getRollNo())
                .course(stu.getCourse())
                .sgender(stu.getGender())
                // Librarian Details
                .lname(lib.getName())
                .lgender(lib.getGender())
                .build();
        return rBookResponse;
    }

}
