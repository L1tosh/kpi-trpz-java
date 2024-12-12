package com.example;

import com.example.domain.*;
import com.example.serializer.Serializer;
import com.example.serializer.impl.JsonSerializer;
import com.example.serializer.impl.XMLSerializer;

public class Main {
    public static void main(String[] args) {
        var userSerialized = new UserSerialized("test@gmail.com", "12345678");
        var productSerialized = new ProductSerialized("test product", "vary test product", 5.12);
        var orderSerialized = new OrderSerialized(1L, userSerialized.getEmail(), productSerialized.getName());

        System.out.println(userSerialized.serialize());
        System.out.println(productSerialized.serialize());
        System.out.println(orderSerialized.serialize());

        var user = new User("test@gmail.com", "12345678");
        var product = new Product("test product", "vary test product", 5.12);
        var order = new Order(1L, user.getEmail(), product.getName());


        Serializer serializer = new JsonSerializer();

        long beforeSerializeWithReflection  = System.nanoTime();
        String jsonWithReflection = serializer.serialize(user);
        long afterSerializeWithReflection = System.nanoTime();

        String separator = "=========================";
        System.out.println(separator);
        System.out.println("Operation time with reflection: " + (afterSerializeWithReflection - beforeSerializeWithReflection));
        System.out.println("Result: " + jsonWithReflection);
        System.out.println(separator);

        long beforeSerializeWithoutReflection = System.nanoTime();
        String jsonWithoutReflection = user.serializeToJson();
        long afterSerializeWithoutReflection = System.nanoTime();

        System.out.println("Operation time without reflection: " + (afterSerializeWithoutReflection - beforeSerializeWithoutReflection));
        System.out.println("Result: " + jsonWithoutReflection);

        System.out.println(separator);

        System.out.println(serializer.serialize(user));
        System.out.println(serializer.serialize(product));
        System.out.println(serializer.serialize(order));

        serializer = new XMLSerializer();

        System.out.println(serializer.serialize(user));
        System.out.println(serializer.serialize(product));
        System.out.println(serializer.serialize(order));

    }
}