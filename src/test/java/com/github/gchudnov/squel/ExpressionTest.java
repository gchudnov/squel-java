package com.github.gchudnov.squel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Expression Tests.
 */
public class ExpressionTest {

    @Test
    public void justAndTest() {
        String actual = Squel.expr()
                .and("bla")
                .toString();

        String expected = "bla";
        assertEquals(expected, actual);
    }

    @Test
    public void justOrTest() {
        String actual = Squel.expr()
                .or("bla")
                .toString();

        String expected = "bla";
        assertEquals(expected, actual);
    }

    @Test
    public void simpleAndTest() {
        String actual = Squel.expr()
                .and("test = 3")
                .toString();

        String expected = "test = 3";
        assertEquals(expected, actual);
    }

    @Test
    public void twoAndTest() {
        String actual = Squel.expr()
                .and("test = 3")
                .and("flight = '4'")
                .toString();

        String expected = "test = 3 AND flight = '4'";
        assertEquals(expected, actual);
    }

}
