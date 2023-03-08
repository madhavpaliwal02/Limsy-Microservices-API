package com.micro.limsy.microservices_librarian.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.micro.limsy.microservices_librarian.dto.LibrarianRequest;
import com.micro.limsy.microservices_librarian.dto.LibrarianResponse;
import com.micro.limsy.microservices_librarian.model.Librarian;
import com.micro.limsy.microservices_librarian.repository.LibrarianRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LibrarianService {

    private final LibrarianRepo librarianRepo;

    /* Create a Librarian */
    public void createLibrarian(LibrarianRequest librarianReq) {
        // Validating whether librarian exists or not
        if (librarianRepo.findAll().stream().filter(
                lib -> lib.getEmail().equals(librarianReq.getEmail())
                        && lib.getName().equals(librarianReq.getName())
                        && lib.getPassword().equals(librarianReq.getPassword()))
                .findAny().isPresent())
            throw new EntityExistsException();

        // If not found then create one
        Librarian librarian = mapToLibrarian(librarianReq);
        librarian.setLibId(UUID.randomUUID().toString());
        librarian.setDate(new Date());

        librarianRepo.save(librarian);
    }

    /* Get all Librarians */
    public List<LibrarianResponse> getAllLibrarian() {
        return librarianRepo.findAll().stream().map(this::mapToLibrarianResponse).toList();
    }

    /* Get a Librarian */
    public LibrarianResponse getLibrarian(String librarianId) {
        Librarian lib = librarianRepo.findAll().stream().filter(librarian -> librarian.getLibId().equals(librarianId))
                .findAny().get();

        if (lib != null)
            return mapToLibrarianResponse(lib);

        throw new EntityNotFoundException("User Not Found...");
    }

    /* Update a Librarian */
    public LibrarianResponse updateLibrarian(LibrarianRequest librarianRequest) {
        // Old details, to be update
        Librarian oldLib = librarianRepo.findAll().stream()
                .filter((librarian) -> librarian.getEmail().equals(librarianRequest.getEmail()))
                .findAny().get();

        // If not found, then
        if (oldLib == null)
            throw new EntityNotFoundException("User Not Found...");

        // Updating details
        Librarian lib = mapToLibrarian(librarianRequest);
        lib.setLibId(oldLib.getLibId());
        lib.setDate(oldLib.getDate());

        librarianRepo.save(lib); // Deleting old details
        librarianRepo.delete(oldLib); // Saving new details

        return mapToLibrarianResponse(lib);
    }

    /* Delete a Librarian */
    public void deleteLibrarian(String librarianId) {
        Librarian lib = librarianRepo.findAll().stream().filter((librarian) -> librarian.getLibId().equals(librarianId))
                .findAny().get();

        if (lib != null) {
            librarianRepo.delete(lib);
            return;
        }

        throw new EntityNotFoundException("User Not Found...");
    }

    /* Mapping Func : Lib -> LibRes */
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

    /* Mapping Func : LibReq -> Lib */
    private Librarian mapToLibrarian(LibrarianRequest librarianRequest) {
        return Librarian.builder()
                .name(librarianRequest.getName())
                .email(librarianRequest.getEmail())
                .password(librarianRequest.getPassword())
                .gender(librarianRequest.getGender())
                .contact(librarianRequest.getContact())
                .build();
    }

}
