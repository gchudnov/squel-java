package com.github.gchudnov.squel;

/**
 * Base class for all builders
 */
public class BaseBuilder {

    protected QueryBuilderOptions mOptions;

    // Constructor
    //
    // options is an Object overriding one or more of cls.DefaultQueryBuilderOptions
    //
    public BaseBuilder(QueryBuilderOptions options) {
        if (options == null) {
            options = new QueryBuilderOptions();
        }

        this.mOptions = options;
    }

    protected String _sanitizeFieldAlias(String value) {
        return (mOptions.autoQuoteAliasNames ? mOptions.fieldAliasQuoteCharacter + value + mOptions.fieldAliasQuoteCharacter : value);
    }

/*
    _sanitizeFieldAlias: (item) ->
      sanitized = @_sanitizeName item, "field alias"

      if @options.autoQuoteAliasNames
        "#{@options.fieldAliasQuoteCharacter}#{sanitized}#{@options.fieldAliasQuoteCharacter}"
      else
        sanitized
     */

    protected String _sanitizeField(QueryBuilder value) {
        return "(" + value.toString() + ")";
    }

    protected String _sanitizeField(String value) {
        return value;
//        if(mOptions.autoQuoteFieldNames) {
//            String quoteChar = mOptions.nameQuoteCharacter;
//            if()
//        }
    }

/*
    _sanitizeField: (item, formattingOptions = {}) ->
      if item instanceof cls.QueryBuilder
        item = "(#{item})"
      else
        item = @_sanitizeName item, "field name"
        if @options.autoQuoteFieldNames
          quoteChar = @options.nameQuoteCharacter

          if formattingOptions.ignorePeriodsForFieldNameQuotes
            # a.b.c -> `a.b.c`
            item = "#{quoteChar}#{item}#{quoteChar}"
          else
            # a.b.c -> `a`.`b`.`c`
            item = item
              .split('.')
              .map( (v) ->
                # treat '*' as special case (#79)
                return if '*' is v then v else "#{quoteChar}#{v}#{quoteChar}"
              )
              .join('.')

      item
 */

    protected String _sanitizeTableAlias(String value) {
        return (mOptions.autoQuoteAliasNames ? mOptions.tableAliasQuoteCharacter + value + mOptions.tableAliasQuoteCharacter : value);
    };
}
