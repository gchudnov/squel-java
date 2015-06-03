package com.github.gchudnov.squel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Expression Tests.
 */
public class ExpressionTest {

    @Test
    public void and() {
        String actual = Squel.expr()
                .and("bla")
                .toString();

        String expected = "bla";
        assertEquals(expected, actual);
    }

    @Test
    public void or() {
        String actual = Squel.expr()
                .or("bla")
                .toString();

        String expected = "bla";
        assertEquals(expected, actual);
    }

    @Test
    public void simpleAnd() {
        String actual = Squel.expr()
                .and("test = 3")
                .toString();

        String expected = "test = 3";
        assertEquals(expected, actual);
    }

    @Test
    public void twoAnd() {
        String actual = Squel.expr()
                .and("test = 3")
                .and("flight = '4'")
                .toString();

        String expected = "test = 3 AND flight = '4'";
        assertEquals(expected, actual);
    }

    @Test
    public void andOr() {
        String actual = Squel.expr()
                .and("test = 3")
                .and("flight = '4'")
                .or("dummy IN (1,2,3)")
                .toString();

        String expected = "test = 3 AND flight = '4' OR dummy IN (1,2,3)";
        assertEquals(expected, actual);
    }

    @Test
    public void andParameter() {
        String actual = Squel.expr()
                .and("test = ?", 3)
                .toString();

        String expected = "test = 3";
        assertEquals(expected, actual);
    }

    @Test
    public void andNull() {
        String actual = Squel.expr()
                .and("test = ?", null)
                .toString();

        String expected = "test = NULL";
        assertEquals(expected, actual);
    }

    @Test
    public void andParameters() {
        String actual = Squel.expr()
                .and("test = ?", 3)
                .and("flight = ?", "4")
                .toString();

        String expected = "test = 3 AND flight = '4'";
        assertEquals(expected, actual);
    }

    @Test
    public void andArrayParameters() {
        String actual = Squel.expr()
                .and("dummy IN ?", new Object[]{ false, 2, null, "str" })
                .toString();

        String expected = "dummy IN (FALSE, 2, NULL, 'str')";
        assertEquals(expected, actual);
    }

    @Test
    public void composite() {
        String actual = Squel.expr()
                .and("test = 4")
                .and_begin()
                    .or("inner = 1")
                    .or("inner = 2")
                .end()
                .or_begin()
                    .and("inner = 3")
                    .and("inner = 4")
                    .or_begin()
                        .or("inner = 5")
                    .end()
                .end()
                .toString();

        String expected = "test = 4 AND (inner = 1 OR inner = 2) OR (inner = 3 AND inner = 4 OR (inner = 5))";
        assertEquals(expected, actual);
    }
}
