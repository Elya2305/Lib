package com.lib.utils.validator.impl;

import com.lib.dto.lib.ClassificationDto;
import com.lib.exception.ValidationException;
import com.lib.repository.ClassificationRepository;
import com.lib.utils.validator.LibraryValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class LibraryValidatorImpl implements LibraryValidator {
    private final ClassificationRepository classificationRepository;

    @Override
    public void validateDeleting(ClassificationDto dto) {
        if (classificationRepository.classificationIsUsed(dto.getId())) {
            throw new ValidationException(String.format("Can't delete %s '%s' because it's used in some books",
                    dto.getType().getTitle().toLowerCase(), dto.getTitle()));
        }
    }

    @Override
    public void validateCreation(ClassificationDto dto) {
        validateNonNull(dto);
        validateTitleExists(() -> classificationRepository.existsByTitleIgnoreCaseAndType(dto.getTitle(), dto.getType()), dto);
    }

    @Override
    public void validateEditing(ClassificationDto dto) {
        validateNonNull(dto);
        validateTitleExists(() -> classificationRepository.existsByTitleIgnoreCaseAndTypeAndIdNot(dto.getTitle(), dto.getType(), dto.getId()), dto);
    }

    private void validateTitleExists(Supplier<Boolean> supplier, ClassificationDto dto) {
        if (supplier.get()) {
            throw new ValidationException(String.format("%s with name '%s' already exists!",
                    dto.getType().getTitle().toLowerCase(), dto.getTitle()));
        }
    }

    private void validateNonNull(ClassificationDto dto) {
        if (isNull(dto) || isNull(dto.getTitle())) {
            throw new ValidationException("Classification must not be empty!");
        }
    }
}
