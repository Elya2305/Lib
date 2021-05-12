package com.lib.repository;

import com.lib.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(value = "select * from books b where lower(b.txt) like concat('%', lower(:search), '%')", nativeQuery = true)
    Page<Book> searchBooks(String search, Pageable pageable);
}
