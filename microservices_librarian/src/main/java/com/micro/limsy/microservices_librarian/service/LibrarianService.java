package com.micro.limsy.microservices_librarian.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.micro.limsy.microservices_librarian.dto.LibrarianRequest;
import com.micro.limsy.microservices_librarian.dto.LibrarianResponse;
import com.micro.limsy.microservices_librarian.model.Librarian;
import com.micro.limsy.microservices_librarian.repository.LibrarianRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LibrarianService {

    private final LibrarianRepo librarianRepo;

    public void createLibrarian(LibrarianRequest librarianReq) {
        // TODO Auto-generated method stub
        Librarian librarian = Librarian.builder()
                .name(librarianReq.getName())
                .email(librarianReq.getEmail())
                .password(librarianReq.getPassword())
                .gender(librarianReq.getGender())
                .contact(librarianReq.getContact())
                .libId(UUID.randomUUID().toString())
                .date(new Date())
                .build();

        librarianRepo.save(librarian);
        log.info("Librarian {} is saved", librarian.getId());
    }

    // Get all Librarians
    public List<LibrarianResponse> getAllLibrarian() {
        List<Librarian> librarians = librarianRepo.findAll();

        return librarians.stream().map(this::mapToLibrarianResponse).toList();
    }

    private LibrarianResponse mapToLibrarianResponse(Librarian librarian) {
        return LibrarianResponse.builder()
                .libId(librarian.getLibId())
                .name(librarian.getName())
                .email(librarian.getEmail())
                .password(librarian.getPassword())
                .gender(librarian.getGender())
                .contact(librarian.getContact())
                .date(librarian.getDate())
                .build();
    }

    public LibrarianResponse getLibrarian(String email) {
        List<Librarian> librarians = librarianRepo.findAll();
        LibrarianResponse librarianResponse = null;

        // LibrarianResponse librarianResponse = librarians.stream().map(librarians ->
        // librarians.getEmail().equals(email))
        for (Librarian librarian : librarians) {
            if (librarian.getEmail().equals(email)) {
                return LibrarianResponse.builder()
                        .libId(librarian.getLibId())
                        .name(librarian.getName())
                        .email(librarian.getEmail())
                        .password(librarian.getPassword())
                        .gender(librarian.getGender())
                        .contact(librarian.getContact())
                        .date(librarian.getDate())
                        .build();
            }
        }
        return librarianResponse;
    }

}
