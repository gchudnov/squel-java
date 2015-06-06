package com.github.gchudnov.squel;

/**
 * JOIN type.
 */
public enum JoinType {
    INNER,
    LEFT,
    RIGHT,
    FULL,
    CROSS;

    static String toSql(JoinType type) {
        String result;
        switch (type) {
            case LEFT:
                result = "LEFT";
                break;

            case RIGHT:
                result = "RIGHT";
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
