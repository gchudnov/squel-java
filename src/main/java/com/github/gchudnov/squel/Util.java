package com.github.gchudnov.squel;

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



//    static <T> T[] append(T[] arr, T lastElement) {
//        final int N = arr.length;
//        arr = java.util.Arrays.copyOf(arr, N+1);
//        arr[N] = lastElement;
//        return arr;
//    }
//    static <T> T[] prepend(T[] arr, T firstElement) {
//        final int N = arr.length;
//        arr = java.util.Arrays.copyOf(arr, N+1);
//        System.arraycopy(arr, 0, arr, 1, N);
//        arr[0] = firstElement;
//        return arr;
//    }
}
