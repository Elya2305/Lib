package com.lib.service.impl;

import com.lib.dto.lib.ClassificationDto;
import com.lib.dto.lib.ClassificationType;
import com.lib.entity.Classification;
import com.lib.repository.ClassificationRepository;
import com.lib.service.ClassificationService;
import com.lib.utils.mapper.LibraryMapper;
import com.lib.utils.validator.LibraryValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClassificationServiceImpl implements ClassificationService {
    private final ClassificationRepository classificationRepository;
    private final LibraryMapper libraryMapper;
    private final LibraryValidator libraryValidator;

    @Override
    public List<ClassificationDto> getByType(ClassificationType type) {
        return map(classificationRepository.findAllByType(type));
    }

    @Override
    public List<ClassificationDto> getAll() {
        return map(classificationRepository.findAll());
    }

    @Override
    public ClassificationDto create(ClassificationDto dto) {
        libraryValidator.validateCreation(dto);
        return save(dto);
    }

    @Override
    public ClassificationDto edit(ClassificationDto dto) {
        libraryValidator.validateEditing(dto);
        return save(dto);
    }

    @Override
    public boolean delete(ClassificationDto dto) {
        libraryValidator.validateDeleting(dto);
        classificationRepository.deleteById(dto.getId());
        return true;
    }

    private ClassificationDto save(ClassificationDto dto) {
        Classification saved = classificationRepository.save(map(dto));
        return map(saved);
    }

    private ClassificationDto map(Classification source) {
        return libraryMapper.createDto(source);
    }

    private Classification map(ClassificationDto source) {
        return libraryMapper.createEntity(source);
    }

    private List<ClassificationDto> map(List<Classification> source) {
        return libraryMapper.mapToClassificationsDto(source);
    }
}
