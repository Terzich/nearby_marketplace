package com.example.nearby.datamodel.dto;

public class CreateProductRequest {

    private String name;
    private String description;
    private Double price;
    private String gpsCoordinates;
    private Integer views;
    private String image;
    private Long categoryId;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getGpsCoordinates() {
        return gpsCoordinates;
    }

    public Integer getViews() {
        return views;
    }

    public String getImage() {
        return image;
    }

    public Long getCategoryId() {
        return categoryId;
    }
}
