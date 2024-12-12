package com.example.domain;

import com.example.annotation.Serialized;
import com.example.serializer.impl.JsonSerializer;

/**
 * Represents a product entity with a name, description, and price.
 */
@Serialized(JsonSerializer.class)
public class Product {
    private String name;
    private String description;
    private Double price;

    /**
     * Constructs a {@code Product} object with the specified name, description, and price.
     *
     * @param name        the name of the product.
     * @param description the description of the product.
     * @param price       the price of the product.
     */
    public Product(String name, String description, Double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
