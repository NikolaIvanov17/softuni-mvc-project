package org.softuni.learningportal.domain.models.view;

import java.math.BigDecimal;

public class OrderBookViewModel {

    private BookDetailsViewModel book;
    private BigDecimal price;

    public OrderBookViewModel() {
    }

    public BookDetailsViewModel getBook() {
        return book;
    }

    public void setBook(BookDetailsViewModel book) {
        this.book = book;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
