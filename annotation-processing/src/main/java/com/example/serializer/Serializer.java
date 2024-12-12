package com.example.serializer;

import com.example.annotation.SerializedName;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class representing the base mechanism for object serialization.
 * Subclasses must implement the {@link #serialize(Object)} method to define
 * their specific serialization logic.
 */
public abstract class Serializer  {

    /**
     * Serializes the given object into a specific format (e.g., JSON, XML).
     *
     * @param obj the object to serialize.
     * @return the serialized representation of the object as a {@code String}.
     */
    public abstract String serialize(Object obj);

    /**
     * Extracts field names and values from the given object, considering
     * {@link SerializedName} annotations if present.
     *
     * @param obj the object from which to extract field data.
     * @return a map containing field names as keys and their values as string representations.
     */
    protected Map<String, String> getFieldValues(Object obj) {
        Map<String, String> fieldValues = new HashMap<>();
        Class<?> currentClass = obj.getClass();

        while (currentClass != null) {
            var fields = currentClass.getDeclaredFields();

            for (Field field : fields) {
                var fieldName = field.getName();
                var annotation = field.getAnnotation(SerializedName.class);

                if (annotation != null) {
                    fieldName = annotation.value();
                }

                try {
                    field.setAccessible(true);
                    Object value = field.get(obj);
                    fieldValues.put(fieldName, value != null ? value.toString() : "null");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            currentClass = currentClass.getSuperclass();
        }

        return fieldValues;
    }
}
