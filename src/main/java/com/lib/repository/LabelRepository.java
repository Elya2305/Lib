package com.lib.repository;

import com.lib.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface LabelRepository extends JpaRepository<Label, Long> {
    Optional<Label> findByTitle(String title);

    @Query(value = "select * from labels lb " +
            "left join book_labels blb " +
            "on lb.id = blb.labels_id " +
            "where blb.book_id =:bookId", nativeQuery = true)
    List<Label> findAllByBook(Long bookId);
}
