package org.softuni.learningportal.integration.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.softuni.learningportal.domain.entities.Book;
import org.softuni.learningportal.domain.models.service.BookServiceModel;
import org.softuni.learningportal.domain.models.service.CategoryServiceModel;
import org.softuni.learningportal.repository.BookRepository;
import org.softuni.learningportal.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookServiceTests {

    @Autowired
    private BookService service;

    @MockBean
    private BookRepository mockBookRepository;

    @Test
    public void createBook_whenValid_bookCreated() {
       BookServiceModel book = new BookServiceModel();
        book.setCategories(List.of(new CategoryServiceModel()));
        when(mockBookRepository.save(any()))
                .thenReturn(new Book());

        service.createBook(book);
        verify(mockBookRepository)
                .save(any());
    }

    @Test(expected = NullPointerException.class)
    public void createBook_whenNull_throw() {
        service.createBook(null);
        verify(mockBookRepository)
                .save(any());
    }

    @Test(expected = Exception.class)
    public void bookService_findBookByIdWithInvalidValue_ThrowError() {
        service.findBookById(null);
        verify(mockBookRepository)
                .save(any());
    }

    @Test(expected = Exception.class)
    public void bookService_deleteBookWithInvalidValue_ThrowError() {
        service.deleteBook(null);
        verify(mockBookRepository)
                .save(any());
    }

    @Test(expected = Exception.class)
    public void bookService_editBookWithInvalidValue_ThrowError() {
        service.editBook(null, null);
        verify(mockBookRepository)
                .save(any());
    }
}
