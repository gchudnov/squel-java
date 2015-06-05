package com.github.gchudnov.squel;

/**
 * Query builder options.
 */
public class QueryBuilderOptions {

    /**
     * Indicates whether table names are rendered inside quotes. Default: FALSE.
     * The quote character used is configurable via the `nameQuoteCharacter` option.
     */
    public boolean autoQuoteTableNames = false;

    /**
     * Indicates whether field names are rendered inside quotes. Default: FALSE.
     * The quote character used is configurable via the nameQuoteCharacter option.
     */
    public boolean autoQuoteFieldNames = false;

    /**
     * Indicates whether alias names are rendered inside quotes. Default: TRUE.
     * The quote character used is configurable via the `tableAliasQuoteCharacter` and `fieldAliasQuoteCharacter` options.
     */
    public boolean autoQuoteAliasNames = true;

    /**
     * Indicates whether to replaces all single quotes within strings. Default: FALSE.
     * The replacement string used is configurable via the `singleQuoteReplacement` option.
     */
    public boolean replaceSingleQuotes = false;

    /**
     * Indicates whether to ignore period (.) when automatically quoting the `field` name. Default: FALSE.
     */
    public boolean ignorePeriodsForFieldNameQuotes = false;

    /**
     * Indicates whether don't quote string values while formatting. Default: FALSE.
     */
    public boolean dontQuote = false;


    /**
     * Specifies the quote character used for when quoting `table` and `field` names.
     */
    public String nameQuoteCharacter = "`";

    /**
     * Specifies the quote character used for when quoting `table alias` names.
     */
    public String tableAliasQuoteCharacter = "`";

    /**
     * Specifies the quote character used for when quoting `field alias` names.
     */
    public String fieldAliasQuoteCharacter = "\"";

    /**
     * Specifies the string to replace single quotes with in query strings.
     */
    public String singleQuoteReplacement = "\'";

    /**
     * Specifies the string to join individual blocks in a query when it's stringified.
     */
    public String separator = " ";
}
