package org.softuni.learningportal.service;

import org.softuni.learningportal.domain.models.service.BookServiceModel;

import java.util.List;

public interface BookService {

    BookServiceModel createBook(BookServiceModel productServiceModel);

    List<BookServiceModel> findAllBooks();

    BookServiceModel findBookById(String id);

    BookServiceModel editBook(String id, BookServiceModel productServiceModel);

    void deleteBook(String id);

    List<BookServiceModel> findAllByCategory(String category);
}
