package com.lib.service.impl;

import com.lib.dto.lib.BookDto;
import com.lib.entity.Book;
import com.lib.exception.NoSuchElementException;
import com.lib.repository.BookRepository;
import com.lib.service.AuthorService;
import com.lib.service.LibraryService;
import com.lib.utils.api.PageDto;
import com.lib.utils.mapper.LibraryMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

import static com.lib.utils.pagination.PagesUtility.createPageableUnsorted;
import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class LibraryServiceImpl implements LibraryService {
    private final BookRepository bookRepository;
    private final LibraryMapper libraryMapper;
    private final AuthorService authorService;

    @Override
    public BookDto createOrUpdateBook(BookDto dto) {
        Book saved = bookRepository.save(libraryMapper.createEntity(dto));
        return libraryMapper.createDto(saved);
    }

    @Override
    public PageDto<BookDto> getBooks(Integer page, Integer pageSize) {
        return getPage(() -> bookRepository.findAll(createPageableUnsorted(page, pageSize)), page);
    }

    @Override
    public PageDto<BookDto> searchBooks(Integer page, Integer pageSize, String search) {
        if (isNull(search)) {
            return getBooks(page, pageSize);
        }
        return getPage(() -> bookRepository.searchBooks(search, createPageableUnsorted(page, pageSize)), page);
    }

    @Override
    public BookDto getBookById(Long id) {
        return map(getFromDb(id));
    }

    @Override
    public boolean deleteBook(Long id) {
        bookRepository.deleteById(id);
        authorService.cleanUpAuthors();
        return true;
    }

    private PageDto<BookDto> getPage(Supplier<Page<Book>> bookSupplier, Integer page) {
        Page<Book> books = bookSupplier.get();
        return PageDto.of(map(books), books.getTotalPages(), page);
    }

    private Book getFromDb(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("There's no book by id " + id));
    }

    private BookDto map(Book book) {
        return libraryMapper.createDto(book);
    }

    private List<BookDto> map(Page<Book> pageBook) {
        return libraryMapper.mapToBooksDto(pageBook.getContent());
    }
}
