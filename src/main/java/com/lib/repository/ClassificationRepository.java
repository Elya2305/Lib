package com.lib.repository;

import com.lib.dto.lib.ClassificationType;
import com.lib.entity.Classification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClassificationRepository extends JpaRepository<Classification, Long> {
    List<Classification> findAllByType(ClassificationType type);

    Optional<Classification> findByTitleAndType(String title, ClassificationType type);

    boolean existsByTitleIgnoreCaseAndType(String title, ClassificationType type);

    boolean existsByTitleIgnoreCaseAndTypeAndIdNot(String title, ClassificationType type, Long id);

    @Query(value = "select count(*) > 0 from \"books_classifications\" where classifications_id =:id", nativeQuery = true)
    boolean classificationIsUsed(Long id);
}
