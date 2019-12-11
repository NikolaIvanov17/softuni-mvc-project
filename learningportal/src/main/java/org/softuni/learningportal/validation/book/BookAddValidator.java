package org.softuni.learningportal.validation.book;

import org.softuni.learningportal.domain.models.binding.BookAddBindingModel;
import org.softuni.learningportal.repository.BookRepository;
import org.softuni.learningportal.validation.ValidationConstants;
import org.softuni.learningportal.validation.annotation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

@Validator
public class BookAddValidator implements org.springframework.validation.Validator{
    private final BookRepository bookRepository;

    @Autowired
    public BookAddValidator(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public boolean supports(Class<?> book) {
        return BookAddBindingModel.class.equals(book);
    }

    @Override
    public void validate(Object object, Errors errors) {
        BookAddBindingModel bookAddBindingModel = (BookAddBindingModel) object;

        if (bookAddBindingModel.getName().length() == 0) {
            errors.rejectValue(
                    "name",
                    ValidationConstants.BOOK_NAME_CANNOT_EMPTY,
                    ValidationConstants.BOOK_NAME_CANNOT_EMPTY
            );
        }

        if (this.bookRepository.findByName(bookAddBindingModel.getName()).isPresent()) {
            errors.rejectValue(
                    "name",
                    String.format(ValidationConstants.NAME_ALREADY_EXISTS, "Книга", bookAddBindingModel.getName()),
                    String.format(ValidationConstants.NAME_ALREADY_EXISTS, "Книга", bookAddBindingModel.getName())
            );
        }
    }
}
