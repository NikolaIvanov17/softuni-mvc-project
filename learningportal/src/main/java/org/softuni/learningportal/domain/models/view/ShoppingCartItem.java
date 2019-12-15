package org.softuni.learningportal.domain.models.view;

import java.io.Serializable;

public class ShoppingCartItem implements Serializable {

    private OrderBookViewModel book;
    private int quantity;

    public ShoppingCartItem() {
    }

    public OrderBookViewModel getBook() {
        return book;
    }

    public void setBook(OrderBookViewModel book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
