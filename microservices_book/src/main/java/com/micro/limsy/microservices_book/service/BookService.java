package com.micro.limsy.microservices_book.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.micro.limsy.microservices_book.dto.BookRequest;
import com.micro.limsy.microservices_book.dto.BookResponse;
import com.micro.limsy.microservices_book.model.Book;
import com.micro.limsy.microservices_book.repository.BookRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepo bookRepo;

    /* Create a Book */
    public void createBook(BookRequest bookRequest) {
        Book book = mapToBook(bookRequest);
        book.setBookId(UUID.randomUUID().toString());
        book.setDate(new Date());

        bookRepo.save(book);
    }

    /* Get all Books */
    public List<BookResponse> getAllBooks() {
        return bookRepo.findAll().stream().map(this::mapToBookResponse).toList();
    }

    /* Get a Book */
    public BookResponse getBook(String bookId) {
        Book book = bookRepo.findAll().stream().filter(b -> b.getBookId().equals(bookId))
                .findAny().get();

        if (book == null)
            throw new EntityNotFoundException("Book is not available");

        return mapToBookResponse(book);
    }

    /* Update a Book */
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
    public void deleteBook(String bookId) {
        Book book = bookRepo.findAll().stream().filter(b -> b.getBookId().equals(bookId))
                .findAny().get();

        if (book == null)
            throw new EntityNotFoundException("Book is not available");

        bookRepo.delete(book);
    }

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
}
