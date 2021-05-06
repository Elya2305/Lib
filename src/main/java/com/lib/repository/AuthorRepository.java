package com.lib.repository;

import com.lib.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByName(String name);

    @Transactional
    @Modifying
    @Query(value = "delete from authors a where a.id not in(select ba.authors_id from books_authors ba)", nativeQuery = true)
    void deleteWastedAuthors();
}
