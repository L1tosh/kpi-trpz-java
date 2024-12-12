package com.example.domain;

import com.example.annotation.Serialized;
import com.example.annotation.SerializedName;
import com.example.serializer.impl.JsonSerializer;

/**
 * Represents a user entity with a username (email) and password.
 * Use {@link SerializedName} annotation for field email.
 */
@Serialized(JsonSerializer.class)
public class User {
    @SerializedName("username")
    private String email;
    private String password;

    /**
     * Constructs a {@code User} object with the specified email and password.
     *
     * @param email    the email of the user.
     * @param password the password of the user.
     */
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String serializeToJson() {
        return "{" +
                "\"username\": " + "\"" + email + "\"" + ", " +
                "\"password\": " + "\"" + password + "\"" +
                "}";
    }
}
