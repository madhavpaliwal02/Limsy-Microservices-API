package com.micro.limsy.microservices_returnbook.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReturnBookResponse {
    private String rBookId;
    private Date date;

    /* Book Data */
    private String title;
    private String authorName;
    private String edition;

    /* Student Data */
    private String sname;
    private String rollNo;
    private String course;
    private String sgender;

    /* Librarian Data */
    private String lname;
    private String lgender;
}
