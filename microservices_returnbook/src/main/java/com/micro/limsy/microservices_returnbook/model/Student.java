package com.micro.limsy.microservices_returnbook.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {
    private String stuId;
    private String name;
    private String email;
    private String password;
    private String rollNo;
    private String enrollment;
    private String course;
    private String semester;
    private String gender;
    private Date date;
}