package com.github.gchudnov.squel;

/**
 * Query builder options.
 */
public class QueryBuilderOptions {

    // If true then table names will be rendered inside quotes. The quote character used is configurable via the
    // nameQuoteCharacter option.
    public boolean autoQuoteTableNames = false;

    //If true then field names will rendered inside quotes. The quote character used is configurable via the
    // nameQuoteCharacter option.
    public boolean autoQuoteFieldNames = false;

    // If true then alias names will rendered inside quotes. The quote character used is configurable via the `tableAliasQuoteCharacter` and `fieldAliasQuoteCharacter` options.
    public boolean autoQuoteAliasNames = true;

    // The quote character used for when quoting table and field names
    public String nameQuoteCharacter = "`";

    // The quote character used for when quoting table alias names
    public String tableAliasQuoteCharacter = "`";

    // The quote character used for when quoting table alias names
    public String fieldAliasQuoteCharacter = "\"";

    // Custom value handlers where key is the value type and the value is the handler function
    // valueHandlers: []

    // Number parameters returned from toParam() as $1, $2, etc. Default is to use '?', startAt 1 will give $1...
    public boolean numberedParameters = false;
    public int numberedParametersStartAt = 1;

    // If true then replaces all single quotes within strings. The replacement string used is configurable via the `singleQuoteReplacement` option.
    public boolean replaceSingleQuotes = false;

    // The string to replace single quotes with in query strings
    public String singleQuoteReplacement = "\'\'";

    // String used to join individual blocks in a query when it's stringified
    public String separator = " ";


    /* TABLE-RELATED */

    // only allow one table to be specified  (default: false)
    public boolean singleTable = false;

    // allow nested query to be specified as a table    (default: false)
    public boolean allowNested = false;

    /* FIELD-RELATED */

    // whether to ignore period (.) when automatically quoting the field name
    public boolean ignorePeriodsForFieldNameQuotes = false;
}
