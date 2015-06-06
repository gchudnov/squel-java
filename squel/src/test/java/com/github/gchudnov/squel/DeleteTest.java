package com.github.gchudnov.squel;

import org.junit.Test;

import java.util.Arrays;

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
    public void whereStringClause() {
        QueryBuilder sql = Squel.delete()
                .from("table2", "t2")
                .where("a = 1");

        String actual = sql.toString();
        String expected = "DELETE FROM table2 `t2` WHERE (a = 1)";
        assertEquals(expected, actual);
    }

    @Test
    public void whereStringAndParamClause() {
        QueryBuilder sql = Squel.delete()
                .from("table2", "t2")
                .where("a = ?", 10);

        String actual = sql.toString();
        String expected = "DELETE FROM table2 `t2` WHERE (a = 10)";
        assertEquals(expected, actual);
    }

    @Test
    public void whereExpressionClause() {
        Expression exp = Squel.expr()
                .and("a = 1");

        QueryBuilder sql = Squel.delete()
                .from("table2", "t2")
                .where(exp);

        String actual = sql.toString();
        String expected = "DELETE FROM table2 `t2` WHERE (a = 1)";
        assertEquals(expected, actual);
    }

    @Test
    public void whereExpressionAndParamClause() {
        Expression exp = Squel.expr()
                .and("a = ?", 12);

        QueryBuilder sql = Squel.delete()
                .from("table2", "t2")
                .where(exp);

        String actual = sql.toString();
        String expected = "DELETE FROM table2 `t2` WHERE (a = 12)";
        assertEquals(expected, actual);
    }

    @Test
    public void joinTableClause() {
        QueryBuilder sql = Squel.delete()
                .from("table2", "t2")
                .where("a = 1")
                .join("other_table", "o", "o.id = t2.id");

        String actual = sql.toString();
        String expected = "DELETE FROM table2 `t2` INNER JOIN other_table `o` ON (o.id = t2.id) WHERE (a = 1)";
        assertEquals(expected, actual);
    }

    @Test
    public void joinTableWithExpressionClause() {
        Expression je = Squel.expr()
                .and("o.id = t2.id");

        QueryBuilder sql = Squel.delete()
                .from("table2", "t2")
                .where("a = 1")
                .join("other_table", "o", je);

        String actual = sql.toString();
        String expected = "DELETE FROM table2 `t2` INNER JOIN other_table `o` ON (o.id = t2.id) WHERE (a = 1)";
        assertEquals(expected, actual);
    }

    @Test
    public void joinQueryClause() {
        QueryBuilder jq = Squel.select()
                .from("other_table");

        QueryBuilder sql = Squel.delete()
                .from("table2", "t2")
                .where("a = 1")
                .join(jq, "o", "o.id = t2.id");

        String actual = sql.toString();
        String expected = "DELETE FROM table2 `t2` INNER JOIN (SELECT * FROM other_table) `o` ON (o.id = t2.id) WHERE (a = 1)";
        assertEquals(expected, actual);
    }

    @Test
    public void joinQueryWithExpressionClause() {
        QueryBuilder jq = Squel.select()
                .from("other_table");

        Expression je = Squel.expr()
                .and("o.id = t2.id");

        QueryBuilder sql = Squel.delete()
                .from("table2", "t2")
                .where("a = 1")
                .join(jq, "o", je);

        String actual = sql.toString();
        String expected = "DELETE FROM table2 `t2` INNER JOIN (SELECT * FROM other_table) `o` ON (o.id = t2.id) WHERE (a = 1)";
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

    //
    // INVALID USAGE
    //

    @Test(expected=UnsupportedOperationException.class)
    public void intoShouldThrow() {
        Squel.delete()
                .into("TABLE");
    }

    @Test(expected=UnsupportedOperationException.class)
    public void fromQueryShouldThrow() {
        Squel.delete()
                .fromQuery(Arrays.asList("F1", "F2"), Squel.select());
    }
}
