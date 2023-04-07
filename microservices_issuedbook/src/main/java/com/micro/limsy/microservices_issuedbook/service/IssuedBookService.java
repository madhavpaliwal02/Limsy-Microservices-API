package com.micro.limsy.microservices_issuedbook.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.micro.limsy.microservices_issuedbook.dto.IssuedBookRequest;
import com.micro.limsy.microservices_issuedbook.dto.IssuedBookResponse;
import com.micro.limsy.microservices_issuedbook.model.Book;
import com.micro.limsy.microservices_issuedbook.model.IssuedBook;
import com.micro.limsy.microservices_issuedbook.model.Librarian;
import com.micro.limsy.microservices_issuedbook.model.Student;
import com.micro.limsy.microservices_issuedbook.repository.IssuedBookRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssuedBookService {

    private final IssuedBookRepo issuedBookRepo;

    private final RestTemplate restTemplate;

    /* Issued a Book */
    public void issueBook(IssuedBookRequest issuedBookRequest) {
        IssuedBook issuedBook = mapToIssuedBook(issuedBookRequest);
        issuedBook.setIBookId(UUID.randomUUID().toString());
        issuedBook.setDate(new Date());
        issuedBookRepo.save(issuedBook);
    }

    /* Get all IssuedBooks */
    public List<IssuedBookResponse> getAllIssuedBooks() {
        List<IssuedBookResponse> IssuedBookResponseList = new ArrayList<>();

        for (IssuedBook ib : issuedBookRepo.findAll()) {
            Librarian lib = restTemplate.getForObject("http://librarian-service/api/librarian/" + ib.getLibrarianId(),
                    Librarian.class);
            Student stu = restTemplate.getForObject("http://student-service/api/student/" + ib.getStudentId(),
                    Student.class);
            Book book = restTemplate.getForObject("http://book-service/api/book/" + ib.getBookId(),
                    Book.class);
            IssuedBookResponseList.add(mapToIssuedBookResponse(lib, stu, book, ib));
        }
        return IssuedBookResponseList;
    }

    /* Get a IssuedBook */
    public IssuedBookResponse getIssuedBook(String ibookId) {
        IssuedBookResponse issuedBookResponse = getAllIssuedBooks().stream()
                .filter(ibookRes -> ibookRes.getIBookId().equals(ibookId))
                .findAny().get();
        System.out.println(issuedBookResponse);
        if (issuedBookResponse != null)
            return issuedBookResponse;
        throw new EntityNotFoundException("IssuedBook not found...");
    }

    /* Delete a IssuedBook */
    public void deleteIssuedBook(String ibookId) {
        IssuedBook issuedBook = issuedBookRepo.findAll().stream()
                .filter(ibook -> ibook.getIBookId().equals(ibookId))
                .findAny().get();
        if (issuedBook == null)
            throw new EntityNotFoundException("IssuedBook not found...");
        issuedBookRepo.delete(issuedBook);
    }

    /* Mapping Function : IssuedBookRequest -> IssuedBook */
    private IssuedBook mapToIssuedBook(IssuedBookRequest issuedBookRequest) {
        return IssuedBook.builder()
                .studentId(issuedBookRequest.getStuId())
                .bookId(issuedBookRequest.getBookId())
                .librarianId(issuedBookRequest.getLibId())
                .build();
    }

    /* Mapping Function : IssuedBook -> IssuedBookResponse */
    private IssuedBookResponse mapToIssuedBookResponse(Librarian librarian, Student student, Book book,
            IssuedBook ibook) {
        return IssuedBookResponse.builder()
                // IssuedBook Details
                .iBookId(ibook.getIBookId())
                .date(ibook.getDate())
                // Book Details
                .title(book.getTitle())
                .authorName(book.getAuthorName())
                .edition(book.getEdition())
                // Student Details
                .sname(student.getName())
                .rollNo(student.getRollNo())
                .course(student.getCourse())
                .sgender(student.getGender())
                // Librarian Details
                .lname(librarian.getName())
                .lgender(librarian.getGender())
                .build();
    }

    /* Get all IssuedBook Objects */
    public List<IssuedBook> getAllIssueBooks() {
        return issuedBookRepo.findAll();
    }

    /* Get a IssuedBook Objects */
    public IssuedBook getAllIssueBooks(String ibookId) {
        IssuedBook iBook = getAllIssueBooks().stream().filter(ibook -> ibook.getIBookId().equals(ibookId))
                .findAny().get();
        if (iBook == null)
            throw new EntityNotFoundException("IssuedBook Not Found");
        return iBook;
    }

}
