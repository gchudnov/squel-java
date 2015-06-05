package com.github.gchudnov.squel.impl;

/**
 * Helper utilities.
 */
public class Util {

    public static boolean isEmpty(String str) {
        return ((str == null || str.length() == 0));
    }

    public static String join(String separator, String... values) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0, len = values.length; i < len; i++) {
            if (i > 0) {
                sb.append(separator);
            }
            sb.append(values[i]);
        }
        return sb.toString();
    }

    public static String joinNonEmpty(String separator, String... values) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0, len = values.length; i < len; i++) {
            if (!isEmpty(values[i])) {
                if (i > 0) {
                    sb.append(separator);
                }
                sb.append(values[i]);
            }
        }
        return sb.toString();
    }
}
