package com.micro.limsy.microservices_returnbook.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private String bookId;
    private String title;
    private String description;
    private String authorName;
    private String genre;
    private String edition;
    private String publicationYear;
    private int pages;
    private int count;
    private Date date;
}
