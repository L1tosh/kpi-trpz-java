package com.example.serializer.impl;

import com.example.serializer.Serializer;

import java.util.Map;

/**
 * Serializer for converting objects to XML format.
 * Extends the {@link Serializer} class and provides XML-specific serialization logic.
 */
public class XMLSerializer extends Serializer {

    /**
     * Serializes the given object into an XML format.
     *
     * @param obj the object to serialize.
     * @return the XML representation of the object as a {@code String}.
     */
    @Override
    public String serialize(Object obj) {
        var fieldValues = getFieldValues(obj);
        var xmlBuilder = new StringBuilder();

        xmlBuilder.append("<").append(obj.getClass().getSimpleName()).append(">");

        for (Map.Entry<String, String> entry : fieldValues.entrySet()) {
            xmlBuilder
                    .append("<").append(entry.getKey()).append(">")
                    .append(escapeXml(entry.getValue()))
                    .append("</").append(entry.getKey()).append(">");
        }

        xmlBuilder.append("</").append(obj.getClass().getSimpleName()).append(">");
        return xmlBuilder.toString();
    }

    /**
     * Escapes special characters in a string to ensure valid XML.
     *
     * @param value the string to escape.
     * @return the escaped string.
     */
    private String escapeXml(String value) {
        if (value == null) {
            return "null";
        }
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}
