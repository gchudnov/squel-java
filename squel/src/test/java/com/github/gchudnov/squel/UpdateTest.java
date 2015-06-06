package com.github.gchudnov.squel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test Update queries.
 */
public class UpdateTest {

    @Test
    public void setParamQurey() {
        QueryBuilder sql = Squel.update()
                .table("table", "t1")
                .set("field", 1);

        String actual = sql.toString();
        String expected = "UPDATE table `t1` SET field = 1";
        assertEquals(expected, actual);
    }

    @Test
    public void setDoubleParam() {
        QueryBuilder sql = Squel.update()
                .table("table", "t1")
                .set("field", 1)
                .set("field2", 1.2);

        String actual = sql.toString();
        String expected = "UPDATE table `t1` SET field = 1, field2 = 1.2";
        assertEquals(expected, actual);
    }

    @Test
    public void setBooleanParam() {
        QueryBuilder sql = Squel.update()
                .table("table", "t1")
                .set("field", 1)
                .set("field2", true);

        String actual = sql.toString();
        String expected = "UPDATE table `t1` SET field = 1, field2 = TRUE";
        assertEquals(expected, actual);
    }

    @Test
    public void setNullParam() {
        QueryBuilder sql = Squel.update()
                .table("table", "t1")
                .set("field", 1)
                .set("field2", null);

        String actual = sql.toString();
        String expected = "UPDATE table `t1` SET field = 1, field2 = NULL";
        assertEquals(expected, actual);
    }

    @Test
    public void setStringParam() {
        QueryBuilder sql = Squel.update()
                .table("table", "t1")
                .set("field", 1)
                .set("field2", "str");

        String actual = sql.toString();
        String expected = "UPDATE table `t1` SET field = 1, field2 = \'str\'";
        assertEquals(expected, actual);
    }

    @Test
    public void setQueryBuilder() {
        QueryBuilder subQuery = Squel.select().field("MAX(score)").from("scores");

        QueryBuilder sql = Squel.update()
                .table("table", "t1")
                .set("field", subQuery);

        String actual = sql.toString();
        String expected = "UPDATE table `t1` SET field = (SELECT MAX(score) FROM scores)";
        assertEquals(expected, actual);
    }

    @Test
    public void whereQuery() {
        QueryBuilder sql = Squel.update()
                .table("table", "t1")
                .set("field", 1)
                .set("field2", null)
                .where("a = 1");

        String actual = sql.toString();
        String expected = "UPDATE table `t1` SET field = 1, field2 = NULL WHERE (a = 1)";
        assertEquals(expected, actual);
    }

    @Test
    public void whereExpressionQuery() {
        Expression exp = Squel.expr()
                .and("a = 1");

        QueryBuilder sql = Squel.update()
                .table("table", "t1")
                .set("field", 1)
                .set("field2", null)
                .where(exp);

        String actual = sql.toString();
        String expected = "UPDATE table `t1` SET field = 1, field2 = NULL WHERE (a = 1)";
        assertEquals(expected, actual);
    }

    @Test
    public void orderQuery() {
        QueryBuilder sql = Squel.update()
                .table("table", "t1")
                .set("field", 1)
                .set("field2", null)
                .where("a = 1")
                .order("a");

        String actual = sql.toString();
        String expected = "UPDATE table `t1` SET field = 1, field2 = NULL WHERE (a = 1) ORDER BY a ASC";
        assertEquals(expected, actual);
    }

    @Test
    public void limitQuery() {
        QueryBuilder sql = Squel.update()
                .table("table", "t1")
                .set("field", 1)
                .set("field2", null)
                .where("a = 1")
                .order("a")
                .limit(2);

        String actual = sql.toString();
        String expected = "UPDATE table `t1` SET field = 1, field2 = NULL WHERE (a = 1) ORDER BY a ASC LIMIT 2";
        assertEquals(expected, actual);
    }
}
