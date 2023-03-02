package com.micro.limsy.microservices_issuedbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IssuedBookRequest {
    private String studentId;
    private String bookId;
    private String librarianId;
}
