package com.lib.utils;

import com.lib.dto.lib.ClassificationDto;
import com.lib.dto.lib.ClassificationType;
import com.lib.exception.ValidationException;
import com.lib.repository.ClassificationRepository;
import com.lib.utils.validator.impl.LibraryValidatorImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LibraryValidatorTest {
    @Mock
    ClassificationRepository classificationRepository;

    @InjectMocks
    LibraryValidatorImpl validator;

    final ClassificationDto fantasy = new ClassificationDto();

    @Before
    public void setUp() {
        fantasy.setIcon("*icon*");
        fantasy.setId(1L);
        fantasy.setTitle("Fantasy");
        fantasy.setType(ClassificationType.GENRE);
    }

    @Test
    public void validateDeleting() {
        when(classificationRepository.classificationIsUsed(fantasy.getId())).thenReturn(true);

        assertThrows(ValidationException.class, () -> validator.validateDeleting(fantasy));
    }

    @Test
    public void validateCreationNull() {
        assertThrows(ValidationException.class, () -> validator.validateCreation(null));
    }

    @Test
    public void validateCreationExists() {
        when(classificationRepository.existsByTitleIgnoreCaseAndType(fantasy.getTitle(), fantasy.getType())).thenReturn(true);

        assertThrows(ValidationException.class, () -> validator.validateCreation(fantasy));
    }

    @Test
    public void validateCreationNotExists() {
        when(classificationRepository.existsByTitleIgnoreCaseAndType(fantasy.getTitle(), fantasy.getType())).thenReturn(false);

        assertDoesNotThrow(() -> validator.validateCreation(fantasy));
    }

    @Test
    public void validateEditingNull() {
        assertThrows(ValidationException.class, () -> validator.validateEditing(null));
    }
}
