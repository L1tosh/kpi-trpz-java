package serializer.impl;

import serializer.Serializer;

import java.util.Iterator;
import java.util.Map;

/**
 * Serializer for converting objects to JSON format.
 * Extends the {@link Serializer} class and provides JSON-specific serialization logic.
 */
public class JsonSerializer extends Serializer {

    /**
     * Serializes the given object into a JSON format.
     *
     * @param obj the object to serialize.
     * @return the JSON representation of the object as a {@code String}.
     */
    @Override
    public String serialize(Object obj) {
        var fieldValues = getFieldValues(obj);
        var jsonBuilder = new StringBuilder("{");

        Iterator<Map.Entry<String, String>> iterator = fieldValues.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            jsonBuilder.append("\"").append(entry.getKey()).append("\": \"").append(entry.getValue()).append("\"");

            if (iterator.hasNext()) {
                jsonBuilder.append(", ");
            }
        }

        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }
}
