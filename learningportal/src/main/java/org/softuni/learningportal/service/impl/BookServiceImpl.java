package org.softuni.learningportal.service.impl;

import org.modelmapper.ModelMapper;
import org.softuni.learningportal.domain.entities.Category;
import org.softuni.learningportal.domain.entities.Book;
import org.softuni.learningportal.domain.models.service.BookServiceModel;
import org.softuni.learningportal.error.BookNameAlreadyExistsException;
import org.softuni.learningportal.error.BookNotFoundException;
import org.softuni.learningportal.repository.OfferRepository;
import org.softuni.learningportal.repository.BookRepository;
import org.softuni.learningportal.service.BookService;
import org.softuni.learningportal.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final OfferRepository offerRepository;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public BookServiceImpl(
            BookRepository bookRepository,
            OfferRepository offerRepository, CategoryService categoryService,
            ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.offerRepository = offerRepository;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public BookServiceModel createBook(BookServiceModel bookServiceModel) {
        Book book = this.bookRepository
                .findByName(bookServiceModel.getName())
                .orElse(null);

        if (book != null) {
            throw new BookNameAlreadyExistsException("Книга с това име, вече съществува.");
        }

        book = this.modelMapper.map(bookServiceModel, Book.class);
        book = this.bookRepository.save(book);

        return this.modelMapper.map(book, BookServiceModel.class);
    }

    @Override
    public List<BookServiceModel> findAllBooks() {
        return this.bookRepository.findAll()
                .stream()
                .map(b -> this.modelMapper.map(b, BookServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public BookServiceModel findBookById(String id) {
        return this.bookRepository.findById(id)
                .map(p -> {
                    BookServiceModel bookServiceModel = this.modelMapper.map(p, BookServiceModel.class);
                    this.offerRepository.findByBook_Id(bookServiceModel.getId())
                            .ifPresent(o -> bookServiceModel.setDiscountedPrice(o.getPrice()));

                    return bookServiceModel;
                })
                .orElseThrow(() -> new BookNotFoundException("Книга с това id не съществува!"));
    }

    @Override
    public BookServiceModel editBook(String id, BookServiceModel bookServiceModel) {
        Book book = this.bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Книга с това id не съществува!"));

        bookServiceModel.setCategories(
                this.categoryService.findAllCategories()
                        .stream()
                        .filter(c -> bookServiceModel.getCategories().contains(c.getId()))
                        .collect(Collectors.toList())
        );

        book.setName(bookServiceModel.getName());
        book.setAuthor(bookServiceModel.getAuthor());
        book.setPrice(bookServiceModel.getPrice());
        book.setCategories(
                bookServiceModel.getCategories()
                        .stream()
                        .map(c -> this.modelMapper.map(c, Category.class))
                        .collect(Collectors.toList())
        );

        this.offerRepository.findByBook_Id(book.getId())
                .ifPresent((o) -> {
                    o.setPrice(book.getPrice().multiply(new BigDecimal(0.8)));

                    this.offerRepository.save(o);
                });

        return this.modelMapper.map(this.bookRepository.saveAndFlush(book), BookServiceModel.class);
    }

    @Override
    public void deleteBook(String id) {
        Book book = this.bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Книга с това id не съществува!"));

        this.bookRepository.delete(book);
    }

    @Override
    public List<BookServiceModel> findAllByCategory(String category) {

        return this.bookRepository.findAll()
                .stream()
                .filter(book -> book.getCategories().stream().anyMatch(categoryStream -> categoryStream.getName().equals(category)))
                .map(book -> this.modelMapper.map(book, BookServiceModel.class))
                .collect(Collectors.toList());
    }


}
