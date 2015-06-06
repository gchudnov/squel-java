package com.github.gchudnov.squel;

/**
 * JOIN type.
 */
public enum JoinType {
    INNER,
    LEFT,
    LEFT_OUTER,
    RIGHT,
    RIGHT_OUTER,
    FULL,
    CROSS;

    static String toSql(JoinType type) {
        String result;
        switch (type) {
            case LEFT:
                result = "LEFT";
                break;

            case LEFT_OUTER:
                result = "LEFT OUTER";
                break;

            case RIGHT:
                result = "RIGHT";
                break;

            case RIGHT_OUTER:
                result = "RIGHT OUTER";
                break;

            case FULL:
                result = "FULL";
                break;

            case CROSS:
                result = "CROSS";
                break;

            case INNER:
            default:
                result = "INNER";
                break;
        }

        return result;
    }
}
