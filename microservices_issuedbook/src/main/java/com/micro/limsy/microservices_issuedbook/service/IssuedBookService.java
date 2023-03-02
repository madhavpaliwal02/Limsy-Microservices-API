package com.micro.limsy.microservices_issuedbook.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    /* Mapping Function : IssuedBookRequest -> IssuedBook */
    private IssuedBook mapToIssuedBook(IssuedBookRequest issuedBookRequest) {
        return IssuedBook.builder()
                .studentId(issuedBookRequest.getStudentId())
                .bookId(issuedBookRequest.getBookId())
                .librarianId(issuedBookRequest.getLibrarianId())
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

    /* Get all IssuedBooks */
    public List<IssuedBookResponse> getAllIssuedBooks() {
        List<IssuedBookResponse> IssuedBookResponseList = new ArrayList<>();

        for (IssuedBook ib : issuedBookRepo.findAll()) {
            Librarian lib = restTemplate.getForObject("http://localhost:8101/api/librarian/" + ib.getLibrarianId(),
                    Librarian.class);
            Student stu = restTemplate.getForObject("http://localhost:8102/api/student/" + ib.getStudentId(),
                    Student.class);
            Book book = restTemplate.getForObject("http://localhost:8103/api/book/" + ib.getBookId(),
                    Book.class);
            IssuedBookResponseList.add(mapToIssuedBookResponse(lib, stu, book, ib));
        }
        return IssuedBookResponseList;
    }


    public IssuedBookResponse getIssuedBook(String ibookId) {
        return getAllIssuedBooks().stream().filter(ibook -> ibook.getIBookId().equals(ibookId))
                .findAny().get();
    }
}
