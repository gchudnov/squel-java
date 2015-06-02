package com.github.gchudnov.squel;

import java.util.ArrayList;
import java.util.List;

/**
 * WHERE
 */
public class WhereBlock extends Block {

    class WhereNode {
        String text;
        List<String> values;

        public WhereNode(String text, List<String> values) {
            this.text = text;
            this.values = values;
        }
    }

    List<WhereNode> mWheres = new ArrayList<>();

    public WhereBlock(QueryBuilderOptions options) {
        super(options);
    }

    // Add a WHERE condition.
    //
    // When the final query is constructed all the WHERE conditions are combined using the intersection (AND) operator.
    public void where(String condition) {
        //condition = @_sanitizeCondition(condition)

        String finalCondition = condition;
        List<String> finalValues = new ArrayList<>();

        if (!Util.isEmpty(finalCondition)) {
            mWheres.add(new WhereNode(finalCondition, finalValues));
        }
    }

    public void where(Expression condition) {
        //condition = @_sanitizeCondition(condition)

        String finalCondition = condition.toString();
        List<String> finalValues = new ArrayList<>();

        if (!Util.isEmpty(finalCondition)) {
            mWheres.add(new WhereNode(finalCondition, finalValues));
        }
    }

    @Override
    public String buildStr(QueryBuilder queryBuilder) {
        if (mWheres.isEmpty())
            return "";

        String whereStr = "";
        for (WhereNode where : mWheres) {
            if (!Util.isEmpty(whereStr)) {
                whereStr += ") AND (";
            }

            if (!where.values.isEmpty()) {
                // TODO: replace placeholders with actual parameter values
            } else {
                whereStr += where.text;
            }
        }

        return "WHERE (" + whereStr + ")";
    }
}

/*
# WHERE
  class cls.com.github.gchudnov.squel.WhereBlock extends cls.com.github.gchudnov.squel.Block
    constructor: (options) ->
      super options
      @wheres = []

    # Add a WHERE condition.
    #
    # When the final query is constructed all the WHERE conditions are combined using the intersection (AND) operator.
    where: (condition, values...) ->
      condition = @_sanitizeCondition(condition)

      finalCondition = ""
      finalValues = []

      # if it's an com.github.gchudnov.squel.Expression instance then convert to text and values
      if condition instanceof cls.com.github.gchudnov.squel.Expression
        t = condition.toParam()
        finalCondition = t.text
        finalValues = t.values
      else
        for idx in [0...condition.length]
          c = condition.charAt(idx)
          if '?' is c and 0 < values.length
            nextValue = values.shift()
            if Array.isArray(nextValue) # where b in (?, ? ?)
              inValues = []
              for item in nextValue
                inValues.push @_sanitizeValue(item)
              finalValues = finalValues.concat(inValues)
              finalCondition += "(#{('?' for item in inValues).join ', '})"
            else
              finalCondition += '?'
              finalValues.push @_sanitizeValue(nextValue)
          else
            finalCondition += c

      if "" isnt finalCondition
        @wheres.push
          text: finalCondition
          values: finalValues


    buildStr: (queryBuilder) ->
      if 0 >= @wheres.length then return ""

      whereStr = ""

      for where in @wheres
        if "" isnt whereStr then whereStr += ") AND ("
        if 0 < where.values.length
          # replace placeholders with actual parameter values
          pIndex = 0
          for idx in [0...where.text.length]
            c = where.text.charAt(idx)
            if '?' is c
              whereStr += @_formatValue( where.values[pIndex++] )
            else
              whereStr += c
        else
          whereStr += where.text

      "WHERE (#{whereStr})"


    buildParam: (queryBuilder) ->
      ret =
        text: ""
        values: []

      if 0 >= @wheres.length then return ret

      whereStr = ""

      for where in @wheres
        if "" isnt whereStr then whereStr += ") AND ("
        str = where.text.split('?')
        i = 0
        for v in where.values
          whereStr += "#{str[i]}" if str[i]?
          p = @_formatValueAsParam(v)
          if (p?.text?)
            whereStr += "(#{p.text})"
            for qv in p.values
              ret.values.push( qv )
          else
            whereStr += "?"
            ret.values.push( p )
          i = i+1
        whereStr += "#{str[i]}" if str[i]?
      ret.text = "WHERE (#{whereStr})"
      ret
 */