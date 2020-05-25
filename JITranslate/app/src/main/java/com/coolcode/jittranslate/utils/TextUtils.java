package com.coolcode.jittranslate.utils;

import java.util.ArrayList;

public class TextUtils {

    private static final char WHITESPACE = ' ';

    public static ArrayList<Integer> getWhitespaceIndexes (String string) {
        int pos = string.indexOf(WHITESPACE, 0);
        ArrayList<Integer> indices = new ArrayList<Integer>();
        while (pos != -1) {
            indices.add(pos);
            pos = string.indexOf(WHITESPACE, pos + 1);
        }
        return indices;
    }

    public static String trimSymbols(String string) {
        int start = 0;
        int end = string.length();
        int i=0;
        for (i=0; i<string.length(); i++) {
            if (Character.isLetterOrDigit(string.charAt(i))) break;
        }
        start = i;
        for (; i<string.length(); i++) {
            if (!Character.isLetterOrDigit(string.charAt(i))) break;
        }
        end = i;
        return string.substring(start, end);
    }
}
