package com.lib.utils.validator;

import com.lib.dto.lib.ClassificationDto;

public interface LibraryValidator {
    void validateCreation(ClassificationDto dto);

    void validateEditing(ClassificationDto dto);

    void validateDeleting(ClassificationDto dto);
}
