package com.example.nearby.datamodel.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class PriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long priceHistoryId;
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;
    private Double price;
    private LocalDateTime timestamp;

    public PriceHistory(){}
    public PriceHistory(Product product, LocalDateTime timestamp) {
        this.product = product;
        this.price = product.getPrice();
        this.timestamp = timestamp;
    }

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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
