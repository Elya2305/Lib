package com.lib.utils.mapper.impl;

import com.lib.dto.lib.*;
import com.lib.entity.Author;
import com.lib.entity.Book;
import com.lib.entity.Classification;
import com.lib.entity.Label;
import com.lib.repository.AuthorRepository;
import com.lib.repository.ClassificationRepository;
import com.lib.repository.LabelRepository;
import com.lib.utils.mapper.LibraryMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
@AllArgsConstructor
public class LibraryMapperImpl implements LibraryMapper {
    private final ClassificationRepository classificationRepository;
    private final LabelRepository labelRepository;
    private final AuthorRepository authorRepository;

    @Override
    public Book createEntity(BookDto dto) {
        Book entity = new Book();
        map(dto, entity);
        return entity;
    }

    @Override
    public Classification createEntity(ClassificationDto dto) {
        Classification entity = new Classification();
        map(dto, entity);
        return entity;
    }

    @Override
    public BookDto createDto(Book entity) {
        BookDto dto = new BookDto();
        map(entity, dto);
        return dto;
    }

    @Override
    public ClassificationDto createDto(Classification entity) {
        ClassificationDto dto = new ClassificationDto();
        map(entity, dto);
        return dto;
    }

    private LabelDto createDto(Label entity) {
        LabelDto dto = new LabelDto();
        map(entity, dto);
        return dto;
    }

    private Classification createOrGetEntity(ClassificationDto dto) {
        Classification entity = createOrGet(dto.getTitle(), dto.getType());
        map(dto, entity);
        return entity;
    }

    private Author createOrGetEntity(String name) {
        return createOrGetAuthor(name);
    }

    private Label createOrGetEntity(LabelDto dto) {
        Label entity = createOrGet(dto.getTitle());
        map(dto, entity);
        return entity;
    }

    private Classification createOrGet(String title, ClassificationType classificationType) {
        return classificationRepository.findByTitleAndType(title, classificationType)
                .orElse(new Classification());
    }

    private Label createOrGet(String title) {
        return labelRepository.findByTitle(title)
                .orElse(new Label());
    }

    private Author createOrGetAuthor(String name) {
        return authorRepository.findByName(name)
                .orElse(new Author(name));
    }

    public void map(BookDto source, Book destination) {
        destination.setId(source.getId());
        destination.setTitle(source.getTitle());
        destination.setClassifications(mapToClassifications(source.getClassification()));
        destination.setSelfLink(source.getSelfLink());
        destination.setImageLink(source.getImageLink());
        destination.setLabels(mapToLabels(source.getLabels()));
        destination.setAuthors(mapToAuthors(source.getAuthors()));
    }

    public void map(Book source, BookDto destination) {
        destination.setId(source.getId());
        destination.setTitle(source.getTitle());
        destination.setClassification(mapToClassificationsDto(source.getClassifications()));
        destination.setAuthors(mapToAuthorNames(source.getAuthors()));
        destination.setImageLink(source.getImageLink());
        destination.setSelfLink(source.getSelfLink());
        destination.setLabels(mapToLabelsDto(source.getLabels()));
    }

    private void map(ClassificationDto source, Classification destination) {
        destination.setId(nonNull(source.getId()) ? source.getId() : destination.getId());
        destination.setTitle(source.getTitle());
        destination.setType(source.getType());
        destination.setIcon(source.getIcon());
    }

    private void map(LabelDto source, Label destination) {
        destination.setId(nonNull(source.getId()) ? source.getId() : destination.getId());
        destination.setTitle(source.getTitle());
        destination.setColor(source.getColor());
    }


    private void map(Classification source, ClassificationDto destination) {
        destination.setId(source.getId());
        destination.setTitle(source.getTitle());
        destination.setType(source.getType());
        destination.setIcon(source.getIcon());
    }

    private void map(Label source, LabelDto destination) {
        destination.setId(source.getId());
        destination.setTitle(source.getTitle());
        destination.setColor(source.getColor());
    }

    @Override
    public List<BookDto> mapToBooksDto(List<Book> source) {
        if (isNull(source) || source.isEmpty()) {
            return Collections.emptyList();
        }

        return source.stream().map(this::createDto).collect(Collectors.toList());
    }

    @Override
    public List<ClassificationDto> mapToClassificationsDto(List<Classification> source) {
        if (isNull(source) || source.isEmpty()) {
            return Collections.emptyList();
        }

        return source.stream().map(this::createDto).collect(Collectors.toList());
    }

    @Override
    public List<LabelDto> mapToLabelsDto(List<Label> source) {
        if (isNull(source) || source.isEmpty()) {
            return Collections.emptyList();
        }

        return source.stream().map(this::createDto).collect(Collectors.toList());
    }

    private List<String> mapToAuthorNames(List<Author> source) {
        if (isNull(source) || source.isEmpty()) {
            return Collections.emptyList();
        }

        return source.stream().map(Author::getName).collect(Collectors.toList());
    }

    private List<Classification> mapToClassifications(List<ClassificationDto> source) {
        if (isNull(source) || source.isEmpty()) {
            return Collections.emptyList();
        }

        return source.stream().map(this::createOrGetEntity).collect(Collectors.toList());
    }

    private List<Author> mapToAuthors(List<String> source) {
        if (isNull(source) || source.isEmpty()) {
            return Collections.emptyList();
        }

        return source.stream().map(this::createOrGetEntity).collect(Collectors.toList());
    }

    private List<Label> mapToLabels(List<LabelDto> source) {
        if (isNull(source) || source.isEmpty()) {
            return Collections.emptyList();
        }

        return source.stream().map(this::createOrGetEntity).collect(Collectors.toList());
    }
}
