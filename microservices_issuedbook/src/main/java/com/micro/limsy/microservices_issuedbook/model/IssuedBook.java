package com.micro.limsy.microservices_issuedbook.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="t_issuedbook")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IssuedBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String iBookId;
    private String studentId;
    private String bookId;
    private String librarianId;
    private Date date;
}
