package com.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to override the field name during serialization.
 * Used to specify an alternative name for a specific field.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SerializedName {
    /**
     * Specifies the alternative name to be used for the serialized field.
     *
     * @return the name to be used during serialization.
     */
    String value();
}

