package com.lib.service.impl;

import com.lib.repository.AuthorRepository;
import com.lib.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public void cleanUpAuthors() {
        authorRepository.deleteWastedAuthors();
    }
}
