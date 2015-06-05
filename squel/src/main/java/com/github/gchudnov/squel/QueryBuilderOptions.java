package com.github.gchudnov.squel;

/**
 * Query builder options.
 */
public class QueryBuilderOptions {

    // If true then table names will be rendered inside quotes. The quote character used is configurable via the
    // nameQuoteCharacter option.
    public boolean autoQuoteTableNames = false;

    //If true then setField names will rendered inside quotes. The quote character used is configurable via the
    // nameQuoteCharacter option.
    public boolean autoQuoteFieldNames = false;

    // If true then alias names will rendered inside quotes. The quote character used is configurable via the `tableAliasQuoteCharacter` and `fieldAliasQuoteCharacter` options.
    public boolean autoQuoteAliasNames = true;

    // The quote character used for when quoting table and setField names
    public String nameQuoteCharacter = "`";

    // The quote character used for when quoting table alias names
    public String tableAliasQuoteCharacter = "`";

    // The quote character used for when quoting table alias names
    public String fieldAliasQuoteCharacter = "\"";

    // If true then replaces all single quotes within strings. The replacement string used is configurable via the `singleQuoteReplacement` option.
    public boolean replaceSingleQuotes = false;

    // The string to replace single quotes with in query strings
    public String singleQuoteReplacement = "\'";

    // String used to setJoin individual blocks in a query when it's stringified
    public String separator = " ";


    /* FIELD-RELATED */

    // whether to ignore period (.) when automatically quoting the setField name
    public boolean ignorePeriodsForFieldNameQuotes = false;

    /* FORMATTING-VALUE */

    // whether quote string values while formatting
    public boolean dontQuote = false;
}
