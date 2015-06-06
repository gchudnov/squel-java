package com.github.gchudnov.squel;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests Validator.
 */
public class ValidatorTest {

    @Test
    public void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<Validator> constructor = Validator.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void sanitizeFieldAutoQuoteFieldNames() {
        QueryBuilderOptions options = new QueryBuilderOptions();
        options.autoQuoteFieldNames = true;

        String fieldName = "a.b.c";
        String actual = Validator.sanitizeField(fieldName, options);
        String expected = "`a`.`b`.`c`";
        assertEquals(expected, actual);
    }

    @Test
    public void sanitizeFieldAutoQuoteFieldNamesStar() {
        QueryBuilderOptions options = new QueryBuilderOptions();
        options.autoQuoteFieldNames = true;

        String fieldName = "a.b.*";
        String actual = Validator.sanitizeField(fieldName, options);
        String expected = "`a`.`b`.*";
        assertEquals(expected, actual);
    }

    @Test
    public void sanitizeFieldAutoQuoteFieldNamesIgnorePeriodsForFieldNameQuotes() {
        QueryBuilderOptions options = new QueryBuilderOptions();
        options.autoQuoteFieldNames = true;
        options.ignorePeriodsForFieldNameQuotes = true;

        String fieldName = "a.b.c";
        String actual = Validator.sanitizeField(fieldName, options);
        String expected = "`a.b.c`";
        assertEquals(expected, actual);
    }

    @Test
    public void formatStringWithoutQuotes() {
        QueryBuilderOptions options = new QueryBuilderOptions();
        options.dontQuote = true;

        String fieldName = "NAME";
        String actual = Validator.formatValue(fieldName, options);
        String expected = "NAME";
        assertEquals(expected, actual);
    }

    @Test
    public void formatStringWithQuotes() {
        QueryBuilderOptions options = new QueryBuilderOptions();
        options.dontQuote = false;

        String fieldName = "NAME";
        String actual = Validator.formatValue(fieldName, options);
        String expected = "'NAME'";
        assertEquals(expected, actual);
    }

    @Test
    public void formatStringReplaceQuutes() {
        QueryBuilderOptions options = new QueryBuilderOptions();
        options.dontQuote = false;
        options.replaceSingleQuotes = true;
        options.singleQuoteReplacement = "#";

        String fieldName = "N'A'ME";
        String actual = Validator.formatValue(fieldName, options);
        String expected = "'N#A#ME'";
        assertEquals(expected, actual);
    }

    @Test
    public void sanitizeTableAutoQuoteTableNames() {
        QueryBuilderOptions options = new QueryBuilderOptions();
        options.autoQuoteTableNames = true;

        String fieldName = "NAME";
        String actual = Validator.sanitizeTable(fieldName, options);
        String expected = "`NAME`";
        assertEquals(expected, actual);
    }

    @Test
    public void sanitizeTableNoAutoQuoteTableNames() {
        QueryBuilderOptions options = new QueryBuilderOptions();
        options.autoQuoteTableNames = false;

        String fieldName = "NAME";
        String actual = Validator.sanitizeTable(fieldName, options);
        String expected = "NAME";
        assertEquals(expected, actual);
    }
}
