package org.softuni.learningportal.service;

import org.softuni.learningportal.domain.models.service.BookServiceModel;

import java.util.List;

public interface BookService {

    BookServiceModel createBook(BookServiceModel bookServiceModel);

    List<BookServiceModel> findAllBooks();

    BookServiceModel findBookById(String id);

    BookServiceModel editBook(String id, BookServiceModel bookServiceModel);

    void deleteBook(String id);

    List<BookServiceModel> findAllByCategory(String category);
}
