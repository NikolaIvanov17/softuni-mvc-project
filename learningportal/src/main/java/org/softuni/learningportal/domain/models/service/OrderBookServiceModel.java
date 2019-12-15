package org.softuni.learningportal.domain.models.service;

import java.math.BigDecimal;

public class OrderBookServiceModel extends BaseServiceModel {

    private BookServiceModel book;
    private BigDecimal price;

    public OrderBookServiceModel() {
    }

    public BookServiceModel getBook() {
        return book;
    }

    public void setBook(BookServiceModel book) {
        this.book = book;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
