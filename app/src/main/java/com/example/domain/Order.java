package com.example.domain;

import com.example.annotation.Serialized;
import com.example.annotation.SerializedName;
import com.example.serializer.impl.XMLSerializer;

/**
 * Represents an order entity with an ID, username, and product name.
 * Use {@link SerializedName} annotation for field userName.
 */
@Serialized(XMLSerializer.class)
public class Order {
    private Long id;
    @SerializedName("userEmail")
    private String username;
    private String productName;

    /**
     * Constructs an {@code Order} object with the specified ID, username, and product name.
     *
     * @param id          the ID of the order.
     * @param userName    the username associated with the order.
     * @param productName the product name associated with the order.
     */
    public Order(Long id, String userName, String productName) {
        this.id = id;
        this.username = userName;
        this.productName = productName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
