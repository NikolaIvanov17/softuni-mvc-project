package org.softuni.learningportal.domain.models.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderServiceModel extends BaseServiceModel {

    private List<OrderBookServiceModel> books;
    private UserServiceModel customer;
    private BigDecimal totalPrice;
    private LocalDateTime createdOn;

    public OrderServiceModel() {
    }

    public List<OrderBookServiceModel> getBooks() {
        return books;
    }

    public void setBooks(List<OrderBookServiceModel> books) {
        this.books = books;
    }

    public UserServiceModel getCustomer() {
        return customer;
    }

    public void setCustomer(UserServiceModel customer) {
        this.customer = customer;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }
}
