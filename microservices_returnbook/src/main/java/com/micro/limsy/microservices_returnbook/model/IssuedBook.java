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
public class IssuedBook {
    private Long id;
    private String iBookId;
    private String studentId;
    private String bookId;
    private String librarianId;
    private Date date;
}
