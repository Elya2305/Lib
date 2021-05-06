package com.lib.utils.mapper;

import com.lib.dto.lib.BookDto;
import com.lib.dto.lib.ClassificationDto;
import com.lib.dto.lib.LabelDto;
import com.lib.entity.Book;
import com.lib.entity.Classification;
import com.lib.entity.Label;

import java.util.List;

public interface LibraryMapper {
    Book createEntity(BookDto dto);

    BookDto createDto(Book entity);

    Classification createEntity(ClassificationDto dto);

    ClassificationDto createDto(Classification entity);

    List<BookDto> mapToBooksDto(List<Book> source);

    List<ClassificationDto> mapToClassificationsDto(List<Classification> source);

    List<LabelDto> mapToLabelsDto(List<Label> source);
}
