package com.lib.service;

import com.lib.dto.lib.ClassificationDto;
import com.lib.dto.lib.ClassificationType;

import java.util.List;

public interface ClassificationService {
    List<ClassificationDto> getByType(ClassificationType type);

    List<ClassificationDto> getAll();

    ClassificationDto create(ClassificationDto dto);

    ClassificationDto edit(ClassificationDto dto);

    boolean delete(ClassificationDto dto);
}
