package com.github.gchudnov.squel;

/**
 * Specifies the type of UNION operation to combines the results of two SQL queries into a single table.
 */
public enum UnionType {
    UNION,
    UNION_ALL;

    static String toSql(UnionType type) {
        String result;
        switch (type) {
            case UNION_ALL:
                result = "UNION ALL";
                break;

            case UNION:
            default:
                result = "UNION";
                break;
        }

        return result;
    }
}
