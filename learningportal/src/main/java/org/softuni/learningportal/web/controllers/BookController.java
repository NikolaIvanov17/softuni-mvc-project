package org.softuni.learningportal.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.learningportal.domain.models.binding.BookAddBindingModel;
import org.softuni.learningportal.domain.models.binding.BookEditBindingModel;
import org.softuni.learningportal.domain.models.binding.CategoryAddBindingModel;
import org.softuni.learningportal.domain.models.binding.CategoryEditBindingModel;
import org.softuni.learningportal.domain.models.service.BookServiceModel;
import org.softuni.learningportal.domain.models.view.BookAllViewModel;
import org.softuni.learningportal.domain.models.view.BookDetailsViewModel;
import org.softuni.learningportal.error.BookNameAlreadyExistsException;
import org.softuni.learningportal.error.BookNotFoundException;
import org.softuni.learningportal.service.CategoryService;
import org.softuni.learningportal.service.BookService;
import org.softuni.learningportal.service.CloudinaryService;
import org.softuni.learningportal.validation.book.BookAddValidator;
import org.softuni.learningportal.validation.book.BookEditValidator;
import org.softuni.learningportal.web.annotations.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/books")
public class BookController extends BaseController {

    private final BookService bookService;
    private final CategoryService categoryService;
    private final BookAddValidator addValidator;
    private final BookEditValidator editValidator;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    @Autowired
    public BookController(BookService bookService, CategoryService categoryService, BookAddValidator addValidator, BookEditValidator editValidator, CloudinaryService cloudinaryService, ModelMapper modelMapper) {
        this.bookService = bookService;
        this.categoryService = categoryService;
        this.addValidator = addValidator;
        this.editValidator = editValidator;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PageTitle("Add Book")
    public ModelAndView addBook() {
        return super.view("book/add-book");
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public ModelAndView addBookConfirm(ModelAndView modelAndView, @ModelAttribute(name = "model") BookAddBindingModel model, BindingResult bindingResult) throws IOException {
        this.addValidator.validate(model, bindingResult);

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("model", model);

            return super.view("book/add-book", modelAndView);
        }

       BookServiceModel bookServiceModel = this.modelMapper.map(model, BookServiceModel.class);

        bookServiceModel.setCategories(
                this.categoryService.findAllCategories()
                        .stream()
                        .filter(c -> model.getCategories().contains(c.getId()))
                        .collect(Collectors.toList())
        );

        bookServiceModel.setImgUrl(this.cloudinaryService.uploadImage(model.getImgUrl()));

        this.bookService.createBook(bookServiceModel);

        return super.redirect("/books/all");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PageTitle("All Books")
    public ModelAndView allBooks(ModelAndView modelAndView) {
        modelAndView.addObject("books", this.bookService.findAllBooks()
                .stream()
                .map(b -> this.modelMapper.map(b, BookAllViewModel.class))
                .sorted(Comparator.comparing(BookAllViewModel::getPrice))
                .collect(Collectors.toList()));

        return super.view("book/all-books", modelAndView);
    }

    @GetMapping("/all-offers")
    //@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PageTitle("All Offers")
    public ModelAndView allOffers(ModelAndView modelAndView) {
        modelAndView.addObject("books", this.bookService.findAllBooks()
                .stream()
                .map(b -> this.modelMapper.map(b, BookAllViewModel.class))
                .sorted(Comparator.comparing(BookAllViewModel::getPrice))
                .collect(Collectors.toList()));

        return super.view("book/all-offers", modelAndView);
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Book Details")
    public ModelAndView detailsBook(@PathVariable String id, ModelAndView modelAndView) {
       BookDetailsViewModel model = this.modelMapper.map(this.bookService.findBookById(id), BookDetailsViewModel.class);

        modelAndView.addObject("book", model);

        return super.view("book/details", modelAndView);
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PageTitle("Edit Book")
    public ModelAndView editBookConfirm(@PathVariable String id, ModelAndView modelAndView, @ModelAttribute(name = "model") BookEditBindingModel model, BindingResult bindingResult) {
        this.editValidator.validate(model, bindingResult);

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("bookId", id);
            modelAndView.addObject("model", model);

            return super.view("book/edit-book", modelAndView);
        }

        BookServiceModel bookServiceModel = this.bookService.findBookById(id);
        BookAddBindingModel bindingModel = this.modelMapper.map(bookServiceModel, BookAddBindingModel.class);
        bindingModel.setCategories(bookServiceModel.getCategories().stream().map(c -> c.getName()).collect(Collectors.toList()));

        modelAndView.addObject("book", bindingModel);
        modelAndView.addObject("bookId", id);

        return super.view("book/edit-book", modelAndView);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public ModelAndView editBookConfirm(@PathVariable String id, @ModelAttribute BookAddBindingModel model) {
        this.bookService.editBook(id, this.modelMapper.map(model, BookServiceModel.class));

        return super.redirect("/books/all");
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PageTitle("Delete Book")
    public ModelAndView deleteBook(@PathVariable String id, ModelAndView modelAndView) {
        BookServiceModel bookServiceModel = this.bookService.findBookById(id);
        BookAddBindingModel model = this.modelMapper.map(bookServiceModel, BookAddBindingModel.class);
        model.setCategories(bookServiceModel.getCategories().stream().map(c -> c.getName()).collect(Collectors.toList()));

        modelAndView.addObject("book", model);
        modelAndView.addObject("bookId", id);

        return super.view("book/delete-book", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public ModelAndView deleteBookConfirm(@PathVariable String id) {
        this.bookService.deleteBook(id);

        return super.redirect("/books/all");
    }

    @GetMapping("/fetch/{category}")
    @ResponseBody
    public List<BookAllViewModel> fetchByCategory(@PathVariable String category) {
        if (category.equals("all")) {
            return this.bookService.findAllBooks()
                    .stream()
                    .map(book -> this.modelMapper.map(book, BookAllViewModel.class))
                    .collect(Collectors.toList());
        }

        return this.bookService.findAllByCategory(category)
                .stream()
                .map(book -> this.modelMapper.map(book, BookAllViewModel.class))
                .collect(Collectors.toList());
    }

    @ExceptionHandler({BookNotFoundException.class})
    public ModelAndView handleBookNotFound(BookNotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("statusCode", e.getStatusCode());

        return modelAndView;
    }

    @ExceptionHandler({BookNameAlreadyExistsException.class})
    public ModelAndView handleBookNameAlreadyExist(BookNameAlreadyExistsException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("statusCode", e.getStatusCode());

        return modelAndView;
    }
}
