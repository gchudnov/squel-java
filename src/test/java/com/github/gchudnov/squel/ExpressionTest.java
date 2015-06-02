package com.github.gchudnov.squel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Expression Test.
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

}
