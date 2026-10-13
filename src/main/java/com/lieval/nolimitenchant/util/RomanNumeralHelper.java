package com.lieval.nolimitenchant.util;

public final class RomanNumeralHelper {
    private static final int[] VALUES = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] NUMERALS = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    private RomanNumeralHelper() {}

    public static String toRomanOrNumber(int number) {
        if (number <= 0) {
            return String.valueOf(number);
        }
        if (number > 3999) {
            return String.valueOf(number);
        }

        StringBuilder sb = new StringBuilder();
        int remaining = number;
        for (int i = 0; i < VALUES.length; i++) {
            while (remaining >= VALUES[i]) {
                remaining -= VALUES[i];
                sb.append(NUMERALS[i]);
            }
        }
        return sb.toString();
    }
}
