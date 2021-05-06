package com.lib.utils;

import com.lib.dto.lib.BookDto;
import com.lib.dto.lib.ClassificationDto;
import com.lib.dto.lib.ClassificationType;
import com.lib.dto.lib.LabelDto;
import com.lib.entity.Author;
import com.lib.entity.Book;
import com.lib.entity.Classification;
import com.lib.entity.Label;
import com.lib.repository.AuthorRepository;
import com.lib.repository.ClassificationRepository;
import com.lib.repository.LabelRepository;
import com.lib.utils.mapper.impl.LibraryMapperImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LibraryMapperTest {
    @Mock
    ClassificationRepository classificationRepository;
    @Mock
    LabelRepository labelRepository;
    @Mock
    AuthorRepository authorRepository;
    @InjectMocks
    LibraryMapperImpl libraryMapper;

    final Book sherlockHolmes = new Book();
    final BookDto sherlockHolmesDto = new BookDto();
    final Classification fantasy = new Classification();
    final ClassificationDto fantasyDto = new ClassificationDto();
    final Label favourite = new Label();
    final LabelDto favouriteDto = new LabelDto();

    @Before
    public void setUp() {
        fantasy.setId(1L);
        fantasy.setTitle("fantsy");
        fantasy.setType(ClassificationType.GENRE);
        fantasy.setIcon("icon");

        fantasyDto.setId(fantasy.getId());
        fantasyDto.setTitle(fantasy.getTitle());
        fantasyDto.setType(fantasy.getType());
        fantasyDto.setIcon(fantasy.getIcon());

        favourite.setId(1L);
        favourite.setTitle("favourite#1");
        favourite.setColor("purple");

        favouriteDto.setId(favourite.getId());
        favouriteDto.setTitle(favourite.getTitle());
        favouriteDto.setColor(favourite.getColor());

        sherlockHolmes.setId(1L);
        sherlockHolmes.setImageLink("*img*");
        sherlockHolmes.setTitle("The Adventures of Sherlock Holmes");
        sherlockHolmes.setAuthors(List.of(new Author(1L, "Arthur Conan Doyle")));
        sherlockHolmes.setClassifications(List.of(fantasy));
        sherlockHolmes.setLabels(List.of(favourite));

        sherlockHolmesDto.setId(sherlockHolmes.getId());
        sherlockHolmesDto.setImageLink(sherlockHolmes.getImageLink());
        sherlockHolmesDto.setTitle(sherlockHolmes.getTitle());
        sherlockHolmesDto.setAuthors(List.of("Arthur Conan Doyle"));
        sherlockHolmesDto.setClassification(List.of(fantasyDto));
        sherlockHolmesDto.setLabels(List.of(favouriteDto));
    }

    @Test
    public void createEntityBook() {
        when(authorRepository.findByName("Arthur Conan Doyle")).thenReturn(Optional.empty());
        sherlockHolmes.setAuthors(List.of(new Author(null, "Arthur Conan Doyle")));

        Book result = libraryMapper.createEntity(sherlockHolmesDto);
        assertEquals(sherlockHolmes, result);
    }

    @Test
    public void createDtoBook() {
        BookDto result = libraryMapper.createDto(sherlockHolmes);
        assertEquals(sherlockHolmesDto, result);
    }


    @Test
    public void createEntityClassification() {
        Classification result = libraryMapper.createEntity(fantasyDto);
        assertEquals(fantasy, result);
    }


    @Test
    public void createDtoClassification() {
        ClassificationDto result = libraryMapper.createDto(fantasy);
        assertEquals(fantasyDto, result);
    }

    @Test
    public void mapToBooksDto() {
        List<BookDto> result = libraryMapper.mapToBooksDto(List.of(sherlockHolmes));
        assertEquals(List.of(sherlockHolmesDto), result);
    }

    @Test
    public void mapToClassificationDto() {
        List<ClassificationDto> result = libraryMapper.mapToClassificationsDto(List.of(fantasy));
        assertEquals(List.of(fantasyDto), result);
    }

    @Test
    public void mapToLabelsDto() {
        List<LabelDto> result = libraryMapper.mapToLabelsDto(List.of(favourite));
        assertEquals(List.of(favouriteDto), result);
    }
}
