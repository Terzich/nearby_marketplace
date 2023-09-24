package com.example.nearby.datamodel.domain;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class PriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long priceHistoryId;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private Double price;
    private Date timestamp;

    public Long getPriceHistoryId() {
        return priceHistoryId;
    }

    public Product getProduct() {
        return product;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
