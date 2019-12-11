package org.softuni.learningportal.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "offers")
public class Offer extends BaseEntity {

    private Book book;
    private BigDecimal price;

    public Offer() {
    }

    @ManyToOne(targetEntity = Book.class)
    @JoinColumn(
            name = "book_id",
            referencedColumnName = "id"
    )
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
