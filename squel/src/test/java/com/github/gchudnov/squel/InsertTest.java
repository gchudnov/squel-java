package com.github.gchudnov.squel;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Insert tests.
 */
public class InsertTest {

    @Test
    public void nullQuery() {
        QueryBuilder sql = Squel.insert()
                .into("table")
                .set("field", null);

        String actual = sql.toString();
        String expected = "INSERT INTO table (field) VALUES (NULL)";
        assertEquals(expected, actual);
    }

    @Test
    public void setInteger() {
        QueryBuilder sql = Squel.insert()
                .into("table")
                .set("field", 1);

        String actual = sql.toString();
        String expected = "INSERT INTO table (field) VALUES (1)";
        assertEquals(expected, actual);
    }

    @Test
    public void setDouble() {
        QueryBuilder sql = Squel.insert()
                .into("table")
                .set("field", 1)
                .set("field2", 1.2);

        String actual = sql.toString();
        String expected = "INSERT INTO table (field, field2) VALUES (1, 1.2)";
        assertEquals(expected, actual);
    }

    @Test
    public void setString() {
        QueryBuilder sql = Squel.insert()
                .into("table")
                .set("field", 1)
                .set("field2", 1.2)
                .set("field3", "str");

        String actual = sql.toString();
        String expected = "INSERT INTO table (field, field2, field3) VALUES (1, 1.2, 'str')";
        assertEquals(expected, actual);
    }

    @Test
    public void setBoolean() {
        QueryBuilder sql = Squel.insert()
                .into("table")
                .set("field", true);

        String actual = sql.toString();
        String expected = "INSERT INTO table (field) VALUES (TRUE)";
        assertEquals(expected, actual);
    }

    @Test
    public void setNull() {
        QueryBuilder sql = Squel.insert()
                .into("table")
                .set("field", null);

        String actual = sql.toString();
        String expected = "INSERT INTO table (field) VALUES (NULL)";
        assertEquals(expected, actual);
    }

    @Test
    public void setQueryBuilder() {
        QueryBuilder subQuery = Squel.select().field("MAX(score)").from("scores");

        QueryBuilder sql = Squel.insert()
                .into("table")
                .set("field", subQuery);

        String actual = sql.toString();
        String expected = "INSERT INTO table (field) VALUES ((SELECT MAX(score) FROM scores))";
        assertEquals(expected, actual);
    }

    @Test
    public void fromQuery() {
        QueryBuilder sel = Squel.select().from("students").where("a = ?", 2);
        QueryBuilder sql = Squel.insert()
                .into("table")
                .fromQuery(Arrays.asList("field1", "field2"), sel);

        String actual = sql.toString();
        String expected = "INSERT INTO table (field1, field2) (SELECT * FROM students WHERE (a = 2))";
        assertEquals(expected, actual);
    }
}
