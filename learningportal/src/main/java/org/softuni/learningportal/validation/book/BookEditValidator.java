package org.softuni.learningportal.validation.book;

import org.softuni.learningportal.domain.models.binding.BookEditBindingModel;
import org.softuni.learningportal.repository.BookRepository;
import org.softuni.learningportal.validation.ValidationConstants;
import org.softuni.learningportal.validation.annotation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

@Validator
public class BookEditValidator implements org.springframework.validation.Validator{

    private final BookRepository bookRepository;

    @Autowired
    public BookEditValidator(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return BookEditBindingModel.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        BookEditBindingModel bookEditBindingModel = (BookEditBindingModel) object;

//        if (bookEditBindingModel.getName().length() == 0) {
//            errors.rejectValue(
//                    "name",
//                    ValidationConstants.BOOK_NAME_CANNOT_EMPTY
//            );
//        }
//
//        if (bookEditBindingModel.getAuthor().length() == 0) {
//            errors.rejectValue(
//                    "author",
//                    ValidationConstants.BOOK_AUTHOR_CANNOT_EMPTY
//            );
//        }
//
//        if (bookEditBindingModel.getPrice().intValue() == 0) {
//            errors.rejectValue(
//                    "price",
//                    ValidationConstants.BOOK_PRICE_CANNOT_EMPTY
//            );
//        }
//
//
//        if (this.bookRepository.findByName(bookEditBindingModel.getName()).isPresent()) {
//            errors.rejectValue(
//                    "name",
//                    String.format(ValidationConstants.NAME_ALREADY_EXISTS, "Книга", bookEditBindingModel.getName()),
//                    String.format(ValidationConstants.NAME_ALREADY_EXISTS, "Книга", bookEditBindingModel.getName())
//            );
//        }
    }
}
