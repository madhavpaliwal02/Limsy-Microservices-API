package com.micro.limsy.microservices_returnbook.model;

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

@Table(name = "t_returnbook")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReturnBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String rbookId;
    private String studentId;
    private String librarianId;
    private String bookId;
    private Date date;
}
