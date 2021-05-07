package com.encoders;

import org.apache.commons.lang3.StringUtils;

public class RunLengthEncoder {

    public String encode(String source) {
        if (StringUtils.isBlank(source)) {
            throw new IllegalArgumentException("source can't be encoded: null or empty");
        }

        String input = source.trim().toUpperCase();
        if (input.length() == 1) {
            return source + "1";
        }

        int inputLength = source.length();

        int count = 1;
        char initialChar = input.charAt(0);
        StringBuilder encoded = new StringBuilder(256);

        for (int i = 1; i < inputLength; i++) {
            char character = input.charAt(i);
            if (character == initialChar) {
                count++;
            } else {
                encoded.append(initialChar).append(count);
                count = 1;
                initialChar = character;
            }

            if (i == inputLength -1) {
                encoded.append(initialChar).append(count);
            }
        }

        return encoded.toString();
    }

    public String decode(String source) {
        checkSourceToDecode(source);

        String input = source.trim().toUpperCase();
        int inputLength = source.length();
        StringBuilder result = new StringBuilder(256);
        StringBuilder countCharacter = new StringBuilder();

        int count;
        char initialCharacter = input.charAt(0);
        for (int i = 1; i < inputLength; i++) {
            char character = input.charAt(i);
            if (Character.isDigit(character)) {
                countCharacter.append(character);
            } else {
                count = Integer.parseInt(countCharacter.toString());
                result.append(repeat(initialCharacter, count));
                initialCharacter = character;
                countCharacter.setLength(0);
            }
        }

        if (StringUtils.isNotEmpty(countCharacter.toString())) {
            count = Integer.parseInt(countCharacter.toString());
            result.append(repeat(initialCharacter, count));
        }

        return result.toString();
    }

    private String repeat(char ch, int count) {
        if (!Character.isLetter(ch)){
            throw new IllegalArgumentException("Cannot decode source has contains invalid characters");
        }

        return StringUtils.repeat(ch, count);
    }

    private void checkSourceToDecode(String source) {
        String message = null;
        if (StringUtils.isBlank(source)) {
            message = "invalid source to be decoded: null or empty";
        } else if (source.trim().length() == 1) {
            message = "invalid source to be decoded: length 1";
        }

        if (StringUtils.isNotEmpty(message)) {
            throw new IllegalArgumentException(message);
        }
    }
}