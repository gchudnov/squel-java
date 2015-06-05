package com.github.gchudnov.squel;

/**
 * UNION or UNION ALL
 */
public enum UnionType {
    UNION,
    UNION_ALL;

    public static String toSql(UnionType type) {
        String result;
        switch (type) {
            case UNION:
                result = "UNION";
                break;

            case UNION_ALL:
                result = "UNION ALL";
                break;

            default:
                result = "UNION";
                break;
        }

        return result;
    }
}
