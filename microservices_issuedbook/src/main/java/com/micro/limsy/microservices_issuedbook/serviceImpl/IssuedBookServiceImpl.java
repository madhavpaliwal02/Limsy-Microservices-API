package com.micro.limsy.microservices_issuedbook.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
import com.micro.limsy.microservices_issuedbook.serviceImpl.service.IssuedBookService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssuedBookServiceImpl implements IssuedBookService {

    private final IssuedBookRepo issuedBookRepo;

    private final RestTemplate restTemplate;

    /* ************************ CRUD Functions ********************** */

    /* Issued a Book */
    @Override
    public void issueBook(IssuedBookRequest issuedBookRequest) {
        IssuedBook issuedBook = mapToIssuedBook(issuedBookRequest);
        issuedBook.setIBookId(UUID.randomUUID().toString());
        issuedBook.setDate(new Date());
        issuedBookRepo.save(issuedBook);
    }

    /* Get all IssuedBooks */
    @Override
    public List<IssuedBookResponse> getAllIssuedBooks() {
        List<IssuedBookResponse> IssuedBookResponseList = new ArrayList<>();

        for (IssuedBook ib : issuedBookRepo.findAll()) {
            Librarian lib = getLibrarianByURL(ib.getLibrarianId());
            Student stu = getStudentByURL(ib.getStudentId());
            Book book = getBookByURL(ib.getBookId());
            IssuedBookResponseList.add(mapToIssuedBookResponse(lib, stu, book, ib));
        }
        return IssuedBookResponseList;
    }

    /* Get a IssuedBook */
    @Override
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
    @Override
    public void deleteIssuedBook(String ibookId) {
        IssuedBook issuedBook = issuedBookRepo.findAll().stream()
                .filter(ibook -> ibook.getIBookId().equals(ibookId))
                .findAny().get();
        if (issuedBook == null)
            throw new EntityNotFoundException("IssuedBook not found...");
        issuedBookRepo.delete(issuedBook);
    }

    /************************** Helper Function ******************/

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

    /* Get Librarian By URL */
    private Librarian getLibrarianByURL(String librarianId) {
        return restTemplate.getForObject("http://librarian-service/api/librarian/" + librarianId,
                Librarian.class);
    }

    /* Get Student By URL */
    private Student getStudentByURL(String studentId) {
        return restTemplate.getForObject("http://student-service/api/student/" + studentId,
                Student.class);
    }

    /* Get Book By URL */
    private Book getBookByURL(String bookId) {
        return restTemplate.getForObject("http://book-service/api/book/" + bookId,
                Book.class);
    }

    /************************* Additional Functions **********************/

    /* Get all IssuedBook Objects */
    @Override
    public List<IssuedBook> getAllIssueBooks() {
        return issuedBookRepo.findAll();
    }

    /* Get a IssuedBook Objects */
    @Override
    public IssuedBook getAllIssueBooks(String ibookId) {
        IssuedBook iBook = getAllIssueBooks().stream().filter(ibook -> ibook.getIBookId().equals(ibookId))
                .findAny().get();
        if (iBook == null)
            throw new EntityNotFoundException("IssuedBook Not Found");
        return iBook;
    }

    /* Get all IssuedBooks for a Librarian */
    @Override
    public List<IssuedBookResponse> getIssuedBooks_Librarian(String librarianId) {
        // Filtering for a librarian
        List<IssuedBook> ibList = this.getAllIssueBooks().stream()
                .filter(ibook -> ibook.getLibrarianId().equals(librarianId))
                .collect(Collectors.toList());

        // Mapping IssuedBook to IssuedBookResponse
        List<IssuedBookResponse> list = new ArrayList<>();
        for (IssuedBook ib : ibList) {
            Librarian lib = getLibrarianByURL(ib.getLibrarianId());
            Student stu = getStudentByURL(ib.getStudentId());
            Book book = getBookByURL(ib.getBookId());
            list.add(mapToIssuedBookResponse(lib, stu, book, ib));
        }
        return list;
    }

    /* Get all IssuedBooks for a Student */
    @Override
    public List<IssuedBook> getIssuedBooks_Student(String studentId) {
        List<IssuedBook> list = new ArrayList<>();

        for (IssuedBook ib : issuedBookRepo.findAll())
            if (ib.getStudentId().equals(studentId))
                list.add(ib);

        return list;
    }

    /* Get Count for IssuedBooks */
    @Override
    public long getCount() {
        return this.issuedBookRepo.count();
    }

}
