package com.lib.service;

import com.lib.dto.lib.BookDto;
import com.lib.entity.Author;
import com.lib.entity.Book;
import com.lib.exception.NoSuchElementException;
import com.lib.repository.BookRepository;
import com.lib.service.impl.LibraryServiceImpl;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * An example of testing service layer
 */
@RunWith(MockitoJUnitRunner.class)
public class LibraryServiceTest {

    @Mock
    BookRepository bookRepository;
    @Mock
    LibraryMapperImpl libraryMapper;
    @InjectMocks
    LibraryServiceImpl libraryService;

    final Book sherlockHolmes = new Book();
    final BookDto sherlockHolmesDto = new BookDto();

    @Before
    public void setUp() {
        sherlockHolmes.setId(1L);
        sherlockHolmes.setImageLink("*img*");
        sherlockHolmes.setTitle("The Adventures of Sherlock Holmes");
        sherlockHolmes.setAuthors(List.of(new Author(1L, "Arthur Conan Doyle")));

        sherlockHolmesDto.setId(sherlockHolmes.getId());
        sherlockHolmesDto.setImageLink(sherlockHolmes.getImageLink());
        sherlockHolmesDto.setTitle(sherlockHolmes.getTitle());
        sherlockHolmesDto.setAuthors(List.of("Arthur Conan Doyle"));

        when(bookRepository.findById(1L)).thenReturn(Optional.of(sherlockHolmes));
        when(libraryMapper.createDto(sherlockHolmes)).thenReturn(sherlockHolmesDto);
        when(libraryMapper.createEntity(sherlockHolmesDto)).thenReturn(sherlockHolmes);

        when(bookRepository.save(sherlockHolmes)).thenReturn(sherlockHolmes);
    }

    @Test
    public void create() {
        sherlockHolmesDto.setId(null);

        BookDto result = libraryService.createOrUpdateBook(sherlockHolmesDto);
        sherlockHolmesDto.setId(sherlockHolmes.getId());
        assertEquals(sherlockHolmesDto, result);
    }

    @Test
    public void getBookById() {
        BookDto result = libraryService.getBookById(1L);
        assertEquals(sherlockHolmesDto, result);
    }

    @Test
    public void getBookByIdNotFound() {
        assertThrows(NoSuchElementException.class, () -> libraryService.getBookById(2L));
    }
}
