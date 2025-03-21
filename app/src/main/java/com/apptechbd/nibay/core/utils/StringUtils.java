package com.apptechbd.nibay.core.utils;

public class StringUtils {
    public String toCamelCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String[] words = input.toLowerCase().split("\\s+|_|-"); // Split by space, underscore, or hyphen
        StringBuilder camelCaseString = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                camelCaseString.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1));
            }
        }

        return camelCaseString.toString();
    }
}
