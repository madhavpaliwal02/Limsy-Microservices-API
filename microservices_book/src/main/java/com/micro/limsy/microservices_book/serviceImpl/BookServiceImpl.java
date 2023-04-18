package com.micro.limsy.microservices_book.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.micro.limsy.microservices_book.dto.BookRequest;
import com.micro.limsy.microservices_book.dto.BookResponse;
import com.micro.limsy.microservices_book.dto.IssuedBook;
import com.micro.limsy.microservices_book.model.Book;
import com.micro.limsy.microservices_book.repository.BookRepo;
import com.micro.limsy.microservices_book.serviceImpl.service.BookService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;
    private final RestTemplate restTemplate;
    private static String url = "";

    /* Create a Book */
    @Override
    public void createBook(BookRequest bookRequest) {
        Book book = mapToBook(bookRequest);
        book.setBookId(UUID.randomUUID().toString());
        book.setDate(new Date());

        bookRepo.save(book);
    }

    /* Get all Books */
    @Override
    public List<BookResponse> getAllBooks() {
        return bookRepo.findAll().stream().map(this::mapToBookResponse).toList();
    }

    /* Get a Book */
    @Override
    public BookResponse getBook(String bookId) {
        Book book = bookRepo.findAll().stream().filter(b -> b.getBookId().equals(bookId))
                .findAny().get();

        if (book == null)
            throw new EntityNotFoundException("Book is not available");

        return mapToBookResponse(book);
    }

    /* Update a Book */
    @Override
    public BookResponse updateBook(BookRequest bookRequest) {
        Book oldBook = bookRepo.findAll().stream().filter(b -> b.getTitle().equals(bookRequest.getTitle())
                && b.getAuthorName().equals(bookRequest.getAuthorName())
                && b.getEdition().equals(bookRequest.getEdition()))
                .findAny().get();

        if (oldBook == null)
            throw new EntityNotFoundException("Book is not available");

        Book book = mapToBook(bookRequest);
        book.setBookId(oldBook.getBookId());
        book.setDate(oldBook.getDate());

        bookRepo.delete(oldBook);
        bookRepo.save(book);

        return mapToBookResponse(book);
    }

    /* Delete a Book */
    @Override
    public void deleteBook(String bookId) {
        Book book = bookRepo.findAll().stream().filter(b -> b.getBookId().equals(bookId))
                .findAny().get();

        if (book == null)
            throw new EntityNotFoundException("Book is not available");

        bookRepo.delete(book);
    }

    /*************************** Helper Functions ***************************/
    /* Mapping : BookRequest -> Book */
    private Book mapToBook(BookRequest bookRequest) {
        return Book.builder()
                .title(bookRequest.getTitle())
                .description(bookRequest.getDescription())
                .authorName(bookRequest.getAuthorName())
                .genre(bookRequest.getGenre())
                .edition(bookRequest.getEdition())
                .publicationYear(bookRequest.getPublicationYear())
                .pages(bookRequest.getPages())
                .count(bookRequest.getCount())
                .build();
    }

    /* Mapping : Book -> BookResponse */
    private BookResponse mapToBookResponse(Book book) {
        return BookResponse.builder()
                .bookId(book.getBookId())
                .title(book.getTitle())
                .description(book.getDescription())
                .authorName(book.getAuthorName())
                .genre(book.getGenre())
                .edition(book.getEdition())
                .publicationYear(book.getPublicationYear())
                .pages(book.getPages())
                .count(book.getCount())
                .date(book.getDate())
                .build();

    }

    /************************** Additional Functions **************************/
    /* Get IssuedBooks for Student */
    @Override
    public List<BookResponse> getIssuedBook_Student(String studentId) {
        // Get All IssuedBooks corresponding to
        url = "http://issuedbook-service/api/issuedbook/student-ib/" + studentId;
        IssuedBook[] ibook = restTemplate.getForObject(url, IssuedBook[].class);

        List<BookResponse> list = new ArrayList<>();

        for (IssuedBook ib : ibook) {
            String bid = ib.getBookId();
            for (BookResponse book : getAllBooks()) {
                if (bid.equals(book.getBookId()))
                    list.add(book);
            }
        }
        return list;
    }

    /* Get count for books */
    @Override
    public long getCount() {
        return this.bookRepo.count();
    }

}
