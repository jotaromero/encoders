package com.encoders;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 'Run Length Encoding' technique which is heavily used in string data compression.
 * Earlier days this is used to compress the black and white photos.
 */
public class RunLengthEncoding {

    public String encode(String source) {
        if (StringUtils.isBlank(source)) {
            throw new IllegalArgumentException("source can't be encoded: null or empty");
        }

        String input = source.trim().toUpperCase();

        int inputLength = source.length();
        StringBuilder encoded = new StringBuilder(256);

        for (int i = 0; i < inputLength; i++) {
            int count = 1;
            while ((i < inputLength - 1) && input.charAt(i) == input.charAt(i + 1)) {
                count++;
                i++;
            }

            encoded.append(input.charAt(i));
            if (count > 1){
                encoded.append(count);
            }
        }

        return encoded.toString();
    }

    public String decode(String source) {
        checkSourceToDecode(source);

        String input = source.trim().toUpperCase();
        StringBuilder result = new StringBuilder(256);

        Pattern pattern = Pattern.compile("[A-Z][0-9]*");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()){
            String group = matcher.group();
            String decoded = getDecodedCharacter(group);
            result.append(decoded);
        }

        return result.toString();
    }

    private String getDecodedCharacter(String group) {
        if (group.length() == 1){
            return group;
        }

        return StringUtils.repeat(group.charAt(0), Integer.parseInt(group.substring(1)));
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