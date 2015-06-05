package com.github.gchudnov.squel;

/**
 * JOIN type.
 */
public enum JoinType {
    INNER,
    LEFT,
    RIGHT,
    LEFT_OUTER,
    FULL,
    CROSS;

    static String toSql(JoinType type) {
        String result;
        switch (type) {
            case INNER:
                result = "INNER";
                break;

            case LEFT:
                result = "LEFT";
                break;

            case RIGHT:
                result = "RIGHT";
                break;

            case LEFT_OUTER:
                result = "LEFT OUTER";
                break;

            case FULL:
                result = "FULL";
                break;

            case CROSS:
                result = "CROSS";
                break;

            default:
                result = "INNER";
                break;
        }

        return result;
    }
}
