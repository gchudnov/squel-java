package com.github.gchudnov.squel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test DELETE.
 */
public class DeleteTest {

    @Test
    public void from() {
        QueryBuilder sql = Squel.delete()
                .from("table");

        String actual = sql.toString();
        String expected = "DELETE FROM table";
        assertEquals(expected, actual);
    }

    @Test
    public void fromAlias() {
        QueryBuilder sql = Squel.delete()
                .from("table2", "t2");

        String actual = sql.toString();
        String expected = "DELETE FROM table2 `t2`";
        assertEquals(expected, actual);
    }

    @Test
    public void whereClause() {
        QueryBuilder sql = Squel.delete()
                .from("table2", "t2")
                .where("a = 1");

        String actual = sql.toString();
        String expected = "DELETE FROM table2 `t2` WHERE (a = 1)";
        assertEquals(expected, actual);
    }

    @Test
    public void joinClause() {
        QueryBuilder sql = Squel.delete()
                .from("table2", "t2")
                .where("a = 1")
                .join("other_table", "o", "o.id = t2.id");

        String actual = sql.toString();
        String expected = "DELETE FROM table2 `t2` INNER JOIN other_table `o` ON (o.id = t2.id) WHERE (a = 1)";
        assertEquals(expected, actual);
    }

    @Test
    public void orderClause() {
        QueryBuilder sql = Squel.delete()
                .from("table2", "t2")
                .where("a = 1")
                .join("other_table", "o", "o.id = t2.id")
                .order("a");

        String actual = sql.toString();
        String expected = "DELETE FROM table2 `t2` INNER JOIN other_table `o` ON (o.id = t2.id) WHERE (a = 1) ORDER BY a ASC";
        assertEquals(expected, actual);
    }

    @Test
    public void limitClause() {
        QueryBuilder sql = Squel.delete()
                .from("table2", "t2")
                .where("a = 1")
                .join("other_table", "o", "o.id = t2.id")
                .order("a")
                .limit(2);

        String actual = sql.toString();
        String expected = "DELETE FROM table2 `t2` INNER JOIN other_table `o` ON (o.id = t2.id) WHERE (a = 1) ORDER BY a ASC LIMIT 2";
        assertEquals(expected, actual);
    }
}
