package com.github.gchudnov.squel;

/**
 * Helper utilities.
 */
class Util {

    private Util() {
    }

    static boolean isEmpty(String str) {
        return ((str == null || str.length() == 0));
    }

    static String join(String separator, Iterable<String> values) {
        StringBuilder sb = new StringBuilder();
        for(String value: values) {
            if (sb.length() > 0) {
                sb.append(separator);
            }
            sb.append(value);
        }
        return sb.toString();
    }

    static String joinNonEmpty(String separator, Iterable<String> values) {
        StringBuilder sb = new StringBuilder();
        for(String value: values) {
            if (!isEmpty(value)) {
                if (sb.length() > 0) {
                    sb.append(separator);
                }
                sb.append(value);
            }
        }
        return sb.toString();
    }
}
