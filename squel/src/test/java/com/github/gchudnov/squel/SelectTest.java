package com.github.gchudnov.squel;

import org.junit.Test;

import java.sql.SQLClientInfoException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Test Selection
 */
public class SelectTest {

    @Test
    public void simpleQuery() {
        String actual = Squel.select()
                .from("table")
                .toString();
        String expected = "SELECT * FROM table";
        assertEquals(expected, actual);
    }

    @Test
    public void twoTablesWithAliasQuery() {
        String actual = Squel.select()
                .from("table")
                .from("table2", "alias2")
                .toString();
        String expected = "SELECT * FROM table, table2 `alias2`";
        assertEquals(expected, actual);
    }

    @Test
    public void setFieldTwice() {
        String actual = Squel.select()
                .from("table")
                .from("table2", "alias2")
                .field("field1", "fa1")
                .field("field1", "fa1")
                .toString();
        String expected = "SELECT field1 AS \"fa1\" FROM table, table2 `alias2`";
        assertEquals(expected, actual);
    }

    @Test
    public void setFieldTwiceDifferentAlias() {
        String actual = Squel.select()
                .from("table")
                .from("table2", "alias2")
                .field("field1", "fa1")
                .field("field1", "fa2")
                .toString();
        String expected = "SELECT field1 AS \"fa1\", field1 AS \"fa2\" FROM table, table2 `alias2`";
        assertEquals(expected, actual);
    }

    @Test
    public void fieldAsQueryWithAlias() {
        String actual = Squel.select()
                .from("table")
                .from("table2", "alias2")
                .field(Squel.select().field("MAX(score)").from("scores"), "fa1")
                .toString();
        String expected = "SELECT (SELECT MAX(score) FROM scores) AS \"fa1\" FROM table, table2 `alias2`";
        assertEquals(expected, actual);
    }

    @Test
    public void fieldAsQueryWithoutAlias() {
        String actual = Squel.select()
                .from("table")
                .from("table2", "alias2")
                .field(Squel.select().field("MAX(score)").from("scores"))
                .toString();
        String expected = "SELECT (SELECT MAX(score) FROM scores) FROM table, table2 `alias2`";
        assertEquals(expected, actual);
    }

    @Test
    public void fieldSelectQuery() {
        String actual = Squel.select()
                .from("table")
                .from("table2", "alias2")
                .field("field1", "fa1").field("field2")
                .toString();
        String expected = "SELECT field1 AS \"fa1\", field2 FROM table, table2 `alias2`";
        assertEquals(expected, actual);
    }

    @Test
    public void fieldsSelectQuery() {
        String actual = Squel.select()
                .from("table")
                .from("table2", "alias2")
                .fields(Arrays.asList("field1", "field2"))
                .toString();
        String expected = "SELECT field1, field2 FROM table, table2 `alias2`";
        assertEquals(expected, actual);
    }

    @Test
    public void distinctQuery() {
        String actual = Squel.select()
                .from("table")
                .from("table2", "alias2")
                .field("field1", "fa1").field("field2")
                .distinct()
                .toString();
        String expected = "SELECT DISTINCT field1 AS \"fa1\", field2 FROM table, table2 `alias2`";
        assertEquals(expected, actual);
    }

    @Test
    public void groupQuery() {
        String actual = Squel.select()
                .from("table")
                .from("table2", "alias2")
                .field("field1", "fa1").field("field2")
                .distinct()
                .group("field").group("field2")
                .toString();
        String expected = "SELECT DISTINCT field1 AS \"fa1\", field2 FROM table, table2 `alias2` GROUP BY field, field2";
        assertEquals(expected, actual);
    }

    @Test
    public void limitQuery() {
        String actual = Squel.select()
                .from("table")
                .limit(10)
                .toString();
        String expected = "SELECT * FROM table LIMIT 10";
        assertEquals(expected, actual);
    }

    @Test
    public void offsetQuery() {
        String actual = Squel.select()
                .from("table")
                .offset(5)
                .toString();
        String expected = "SELECT * FROM table OFFSET 5";
        assertEquals(expected, actual);
    }

    @Test
    public void orderByAscQuery() {
        String actual = Squel.select()
                .from("table")
                .order("id")
                .toString();
        String expected = "SELECT * FROM table ORDER BY id ASC";
        assertEquals(expected, actual);
    }

    @Test
    public void orderByDescQuery() {
        String actual = Squel.select()
                .from("table")
                .order("id", SortOrder.DESC)
                .toString();
        String expected = "SELECT * FROM table ORDER BY id DESC";
        assertEquals(expected, actual);
    }

    @Test
    public void ordersQuery() {
        String actual = Squel.select()
                .from("table")
                .order("id", SortOrder.ASC)
                .order("sid", SortOrder.DESC)
                .toString();
        String expected = "SELECT * FROM table ORDER BY id ASC, sid DESC";
        assertEquals(expected, actual);
    }

    @Test
    public void whereQuery() {
        String actual = Squel.select()
                .from("table")
                .where("a = 1")
                .toString();
        String expected = "SELECT * FROM table WHERE (a = 1)";
        assertEquals(expected, actual);
    }

    @Test
    public void whereParameterQuery() {
        String actual = Squel.select()
                .from("table")
                .where("a = ?", 1)
                .toString();
        String expected = "SELECT * FROM table WHERE (a = 1)";
        assertEquals(expected, actual);
    }

    @Test
    public void whereExpressionParameterQuery() {
        QueryBuilder q = Squel.select()
                .from("table")
                .from("table2", "alias2")
                .field("field1", "fa1").field("field2")
                .distinct()
                .group("field").group("field2");
        QueryBuilder cond = Squel.select().field("MAX(score)").from("scores");
        QueryBuilder sql = q.where("a = ?", cond);

        String actual = sql.toString();
        String expected = "SELECT DISTINCT field1 AS \"fa1\", field2 FROM table, table2 `alias2` WHERE (a = (SELECT MAX(score) FROM scores)) GROUP BY field, field2";
        assertEquals(expected, actual);
    }

    @Test
    public void whereExpressionQuery() {
        QueryBuilder q = Squel.select()
                .from("table")
                .from("table2", "alias2")
                .field("field1", "fa1").field("field2")
                .distinct()
                .group("field").group("field2");
        Expression expr = Squel.expr()
                .and("a = ?", 1)
                .andBegin()
                    .or("b = ?", 2)
                    .or("c = ?", 3)
                .end();
        QueryBuilder sql = q.where(expr);

        String actual = sql.toString();
        String expected = "SELECT DISTINCT field1 AS \"fa1\", field2 FROM table, table2 `alias2` WHERE (a = 1 AND (b = 2 OR c = 3)) GROUP BY field, field2";
        assertEquals(expected, actual);
    }

    @Test
    public void whereExpressionSubQuery() {
        QueryBuilder q = Squel.select()
                .from("table")
                .from("table2", "alias2")
                .field("field1", "fa1").field("field2")
                .distinct()
                .group("field").group("field2");
        QueryBuilder subQuery = Squel.select().field("field1").from("table1").where("field2 = ?", 10);
        Expression exp = Squel.expr()
                .and("a = ?", subQuery)
                .andBegin()
                    .or("b = ?", 2)
                    .or("c = ?", 3)
                .end();
        QueryBuilder sql = q.where(exp);
        String actual = sql.toString();
        String expected = "SELECT DISTINCT field1 AS \"fa1\", field2 FROM table, table2 `alias2` WHERE (a = (SELECT field1 FROM table1 WHERE (field2 = 10)) AND (b = 2 OR c = 3)) GROUP BY field, field2";
        assertEquals(expected, actual);
    }

    @Test
    public void whereNullQuery() {
        QueryBuilder sql = Squel.select()
                .from("table")
                .from("table2", "alias2")
                .field("field1", "fa1").field("field2")
                .distinct()
                .group("field").group("field2")
                .where("a = ?", null);
        String actual = sql.toString();
        String expected = "SELECT DISTINCT field1 AS \"fa1\", field2 FROM table, table2 `alias2` WHERE (a = NULL) GROUP BY field, field2";
        assertEquals(expected, actual);
    }

    @Test
    public void nestedQuery() {
        QueryBuilder inner1 = Squel.select().from("students");
        QueryBuilder inner2 = Squel.select().from("scores");

        String actual = Squel.select()
                .from(inner1)
                .from(inner2, "scores")
                .toString();

        String expected = "SELECT * FROM (SELECT * FROM students), (SELECT * FROM scores) `scores`";
        assertEquals(expected, actual);
    }

    @Test
    public void deepNestedQuery() {
        QueryBuilder inner1 = Squel.select().from("students");
        QueryBuilder inner2 = Squel.select().from(inner1);

        String actual = Squel.select()
                .from(inner2)
                .toString();

        String expected = "SELECT * FROM (SELECT * FROM (SELECT * FROM students))";
        assertEquals(expected, actual);
    }

    @Test
    public void nestingInJoinsQuery() {
        QueryBuilder inner1 = Squel.select().from("students");
        QueryBuilder inner2 = Squel.select().from(inner1);

        String actual = Squel.select()
                .from("schools")
                .join(inner2, "meh", "meh.ID = ID")
                .toString();

        String expected = "SELECT * FROM schools INNER JOIN (SELECT * FROM (SELECT * FROM students)) `meh` ON (meh.ID = ID)";
        assertEquals(expected, actual);
    }

    @Test
    public void joinExpression() {
        QueryBuilder inst = Squel.select()
                .from("table")
                .from("table2", "alias2")
                .field("field1", "fa1").field("field2")
                .distinct()
                .group("field").group("field2")
                .where("a = 1");

        QueryBuilder subQuery = Squel.select().field("abc").from("table1").where("adf = ?", "today1");
        QueryBuilder subQuery2 = Squel.select().field("xyz").from("table2").where("adf = ?", "today2");
        Expression expr = Squel.expr().and("field1 = ?", subQuery);

        QueryBuilder sql = inst
                .join("other_table", expr)
                .where("def IN ?", subQuery2);

        String actual = sql.toString();
        String expected = "SELECT DISTINCT field1 AS \"fa1\", field2 FROM table, table2 `alias2` INNER JOIN other_table ON (field1 = (SELECT abc FROM table1 WHERE (adf = 'today1'))) WHERE (a = 1) AND (def IN (SELECT xyz FROM table2 WHERE (adf = 'today2'))) GROUP BY field, field2";
        assertEquals(expected, actual);
    }

    @Test
    public void nestingJoinWithParams() {
        QueryBuilder inner1 = Squel.select().from("students").where("age = ?", 6);
        QueryBuilder inner2 = Squel.select().from(inner1);

        QueryBuilder sql = Squel.select().from("schools").where("school_type = ?", "junior").join(inner2, "meh", "meh.ID = ID");

        String actual = sql.toString();
        String expected = "SELECT * FROM schools INNER JOIN (SELECT * FROM (SELECT * FROM students WHERE (age = 6))) `meh` ON (meh.ID = ID) WHERE (school_type = 'junior')";
        assertEquals(expected, actual);
    }

    @Test
    public void severalJoins() {
        String actual = Squel.select()
                .from("schools")
                .join("students", "st", "st.ID = ID")
                .join("equipment", "eq", "eq.ID = ID")
                .toString();

        String expected = "SELECT * FROM schools INNER JOIN students `st` ON (st.ID = ID) INNER JOIN equipment `eq` ON (eq.ID = ID)";
        assertEquals(expected, actual);
    }

    @Test
    public void joinWithAlias() {
        String actual = Squel.select()
                .from("schools")
                .join("students", "st")
                .toString();

        String expected = "SELECT * FROM schools INNER JOIN students `st`";
        assertEquals(expected, actual);
    }

    @Test
    public void joinWithoutCondition() {
        String actual = Squel.select()
                .from("schools")
                .join("students")
                .toString();

        String expected = "SELECT * FROM schools INNER JOIN students";
        assertEquals(expected, actual);
    }

    @Test
    public void joinEmptyCondition() {
        String actual = Squel.select()
                .from("schools")
                .join("students", "s", "")
                .toString();

        String expected = "SELECT * FROM schools INNER JOIN students `s`";
        assertEquals(expected, actual);
    }

    @Test
    public void innerJoinTable() {
        String actual = Squel.select()
                .from("schools", "sc")
                .innerJoin("students")
                .toString();

        String expected = "SELECT * FROM schools `sc` INNER JOIN students";
        assertEquals(expected, actual);
    }

    @Test
    public void innerJoinTableExpression() {
        String actual = Squel.select()
                .from("schools", "sc")
                .innerJoin("students", Squel.expr().and("sc.id = students.school"))
                .toString();

        String expected = "SELECT * FROM schools `sc` INNER JOIN students ON (sc.id = students.school)";
        assertEquals(expected, actual);
    }

    @Test
    public void innerJoinTableAliasExpression() {
        String actual = Squel.select()
                .from("schools", "sc")
                .innerJoin("students", "st", Squel.expr().and("sc.id = st.school"))
                .toString();

        String expected = "SELECT * FROM schools `sc` INNER JOIN students `st` ON (sc.id = st.school)";
        assertEquals(expected, actual);
    }

    @Test
    public void innerJoinQueryBuilderAliasCondition() {
        String actual = Squel.select()
                .from("schools", "sc")
                .innerJoin(Squel.select().from("students"), "st", "sc.id = st.school")
                .toString();

        String expected = "SELECT * FROM schools `sc` INNER JOIN (SELECT * FROM students) `st` ON (sc.id = st.school)";
        assertEquals(expected, actual);
    }

    @Test
    public void innerJoinQueryBuilderAliasExpression() {
        String actual = Squel.select()
                .from("schools", "sc")
                .innerJoin(Squel.select().from("students"), "st", Squel.expr().and("sc.id = st.school"))
                .toString();

        String expected = "SELECT * FROM schools `sc` INNER JOIN (SELECT * FROM students) `st` ON (sc.id = st.school)";
        assertEquals(expected, actual);
    }

    @Test
    public void innerJoinTableAlias() {
        String actual = Squel.select()
                .from("schools", "sc")
                .innerJoin("students", "s")
                .toString();

        String expected = "SELECT * FROM schools `sc` INNER JOIN students `s`";
        assertEquals(expected, actual);
    }

    @Test
    public void leftJoinTable() {
        String actual = Squel.select()
                .from("schools", "sc")
                .leftJoin("students")
                .toString();

        String expected = "SELECT * FROM schools `sc` LEFT JOIN students";
        assertEquals(expected, actual);
    }

    @Test
    public void leftJoinTableAlias() {
        String actual = Squel.select()
                .from("schools", "sc")
                .leftJoin("students", "s")
                .toString();

        String expected = "SELECT * FROM schools `sc` LEFT JOIN students `s`";
        assertEquals(expected, actual);
    }

    @Test
    public void leftJoinTableExpression() {
        String actual = Squel.select()
                .from("schools", "sc")
                .leftJoin("students", Squel.expr().and("sc.id = students.school"))
                .toString();

        String expected = "SELECT * FROM schools `sc` LEFT JOIN students ON (sc.id = students.school)";
        assertEquals(expected, actual);
    }

    @Test
    public void leftJoinTableAliasExpression() {
        String actual = Squel.select()
                .from("schools", "sc")
                .leftJoin("students", "st", Squel.expr().and("sc.id = st.school"))
                .toString();

        String expected = "SELECT * FROM schools `sc` LEFT JOIN students `st` ON (sc.id = st.school)";
        assertEquals(expected, actual);
    }

    @Test
    public void leftJoinQueryBuilderAliasCondition() {
        String actual = Squel.select()
                .from("schools", "sc")
                .leftJoin(Squel.select().from("students"), "st", "sc.id = st.school")
                .toString();

        String expected = "SELECT * FROM schools `sc` LEFT JOIN (SELECT * FROM students) `st` ON (sc.id = st.school)";
        assertEquals(expected, actual);
    }

    @Test
    public void leftJoinQueryBuilderAliasExpression() {
        String actual = Squel.select()
                .from("schools", "sc")
                .leftJoin(Squel.select().from("students"), "st", Squel.expr().and("sc.id = st.school"))
                .toString();

        String expected = "SELECT * FROM schools `sc` LEFT JOIN (SELECT * FROM students) `st` ON (sc.id = st.school)";
        assertEquals(expected, actual);
    }

    @Test
    public void rightJoinTable() {
        String actual = Squel.select()
                .from("schools", "sc")
                .rightJoin("students")
                .toString();

        String expected = "SELECT * FROM schools `sc` RIGHT JOIN students";
        assertEquals(expected, actual);
    }

    @Test
    public void rightJoinTableAlias() {
        String actual = Squel.select()
                .from("schools", "sc")
                .rightJoin("students", "s")
                .toString();

        String expected = "SELECT * FROM schools `sc` RIGHT JOIN students `s`";
        assertEquals(expected, actual);
    }

    @Test
    public void rightJoinTableExpression() {
        String actual = Squel.select()
                .from("schools", "sc")
                .rightJoin("students", Squel.expr().and("sc.id = students.school"))
                .toString();

        String expected = "SELECT * FROM schools `sc` RIGHT JOIN students ON (sc.id = students.school)";
        assertEquals(expected, actual);
    }

    @Test
    public void rightJoinTableAliasExpression() {
        String actual = Squel.select()
                .from("schools", "sc")
                .rightJoin("students", "st", Squel.expr().and("sc.id = st.school"))
                .toString();

        String expected = "SELECT * FROM schools `sc` RIGHT JOIN students `st` ON (sc.id = st.school)";
        assertEquals(expected, actual);
    }

    @Test
    public void rightJoinQueryBuilderAliasCondition() {
        String actual = Squel.select()
                .from("schools", "sc")
                .rightJoin(Squel.select().from("students"), "st", "sc.id = st.school")
                .toString();

        String expected = "SELECT * FROM schools `sc` RIGHT JOIN (SELECT * FROM students) `st` ON (sc.id = st.school)";
        assertEquals(expected, actual);
    }

    @Test
    public void rightJoinQueryBuilderAliasExpression() {
        String actual = Squel.select()
                .from("schools", "sc")
                .rightJoin(Squel.select().from("students"), "st", Squel.expr().and("sc.id = st.school"))
                .toString();

        String expected = "SELECT * FROM schools `sc` RIGHT JOIN (SELECT * FROM students) `st` ON (sc.id = st.school)";
        assertEquals(expected, actual);
    }

    @Test
    public void fullJoinTable() {
        String actual = Squel.select()
                .from("schools", "sc")
                .fullJoin("students")
                .toString();

        String expected = "SELECT * FROM schools `sc` FULL JOIN students";
        assertEquals(expected, actual);
    }

    @Test
    public void fullJoinTableAlias() {
        String actual = Squel.select()
                .from("schools", "sc")
                .fullJoin("students", "s")
                .toString();

        String expected = "SELECT * FROM schools `sc` FULL JOIN students `s`";
        assertEquals(expected, actual);
    }

    @Test
    public void fullJoinTableExpression() {
        String actual = Squel.select()
                .from("schools", "sc")
                .fullJoin("students", Squel.expr().and("sc.id = students.school"))
                .toString();

        String expected = "SELECT * FROM schools `sc` FULL JOIN students ON (sc.id = students.school)";
        assertEquals(expected, actual);
    }

    @Test
    public void fullJoinTableAliasExpression() {
        String actual = Squel.select()
                .from("schools", "sc")
                .fullJoin("students", "st", Squel.expr().and("sc.id = st.school"))
                .toString();

        String expected = "SELECT * FROM schools `sc` FULL JOIN students `st` ON (sc.id = st.school)";
        assertEquals(expected, actual);
    }

    @Test
    public void fullJoinQueryBuilderAliasCondition() {
        String actual = Squel.select()
                .from("schools", "sc")
                .fullJoin(Squel.select().from("students"), "st", "sc.id = st.school")
                .toString();

        String expected = "SELECT * FROM schools `sc` FULL JOIN (SELECT * FROM students) `st` ON (sc.id = st.school)";
        assertEquals(expected, actual);
    }

    @Test
    public void fullJoinQueryBuilderAliasExpression() {
        String actual = Squel.select()
                .from("schools", "sc")
                .fullJoin(Squel.select().from("students"), "st", Squel.expr().and("sc.id = st.school"))
                .toString();

        String expected = "SELECT * FROM schools `sc` FULL JOIN (SELECT * FROM students) `st` ON (sc.id = st.school)";
        assertEquals(expected, actual);
    }

    @Test
    public void crossJoinTable() {
        String actual = Squel.select()
                .from("schools", "sc")
                .crossJoin("students")
                .toString();

        String expected = "SELECT * FROM schools `sc` CROSS JOIN students";
        assertEquals(expected, actual);
    }

    @Test
    public void crossJoinTableAlias() {
        String actual = Squel.select()
                .from("schools", "sc")
                .crossJoin("students", "s")
                .toString();

        String expected = "SELECT * FROM schools `sc` CROSS JOIN students `s`";
        assertEquals(expected, actual);
    }

    @Test
    public void crossJoinTableExpression() {
        String actual = Squel.select()
                .from("schools", "sc")
                .crossJoin("students", Squel.expr().and("sc.id = students.school"))
                .toString();

        String expected = "SELECT * FROM schools `sc` CROSS JOIN students ON (sc.id = students.school)";
        assertEquals(expected, actual);
    }

    @Test
    public void crossJoinTableAliasExpression() {
        String actual = Squel.select()
                .from("schools", "sc")
                .crossJoin("students", "st", Squel.expr().and("sc.id = st.school"))
                .toString();

        String expected = "SELECT * FROM schools `sc` CROSS JOIN students `st` ON (sc.id = st.school)";
        assertEquals(expected, actual);
    }

    @Test
    public void crossJoinQueryBuilderAliasCondition() {
        String actual = Squel.select()
                .from("schools", "sc")
                .crossJoin(Squel.select().from("students"), "st", "sc.id = st.school")
                .toString();

        String expected = "SELECT * FROM schools `sc` CROSS JOIN (SELECT * FROM students) `st` ON (sc.id = st.school)";
        assertEquals(expected, actual);
    }

    @Test
    public void crossJoinQueryBuilderAliasExpression() {
        String actual = Squel.select()
                .from("schools", "sc")
                .crossJoin(Squel.select().from("students"), "st", Squel.expr().and("sc.id = st.school"))
                .toString();

        String expected = "SELECT * FROM schools `sc` CROSS JOIN (SELECT * FROM students) `st` ON (sc.id = st.school)";
        assertEquals(expected, actual);
    }

    @Test
    public void innerJoin() {
        String actual = Squel.select()
                .from("schools", "sc")
                .innerJoin("students", "s", "sc.id = s.school")
                .toString();

        String expected = "SELECT * FROM schools `sc` INNER JOIN students `s` ON (sc.id = s.school)";
        assertEquals(expected, actual);
    }

    @Test
    public void leftJoin() {
        String actual = Squel.select()
                .from("schools", "sc")
                .leftJoin("students", "s", "sc.id = s.school")
                .toString();

        String expected = "SELECT * FROM schools `sc` LEFT JOIN students `s` ON (sc.id = s.school)";
        assertEquals(expected, actual);
    }

    @Test
    public void rightJoin() {
        String actual = Squel.select()
                .from("schools", "sc")
                .rightJoin("students", "s", "sc.id = s.school")
                .toString();

        String expected = "SELECT * FROM schools `sc` RIGHT JOIN students `s` ON (sc.id = s.school)";
        assertEquals(expected, actual);
    }

    @Test
    public void fullJoin() {
        String actual = Squel.select()
                .from("schools", "sc")
                .fullJoin("students", "s", "sc.id = s.school")
                .toString();

        String expected = "SELECT * FROM schools `sc` FULL JOIN students `s` ON (sc.id = s.school)";
        assertEquals(expected, actual);
    }

    @Test
    public void crossJoin() {
        String actual = Squel.select()
                .from("schools", "sc")
                .crossJoin("students", "s", "sc.id = s.school")
                .toString();

        String expected = "SELECT * FROM schools `sc` CROSS JOIN students `s` ON (sc.id = s.school)";
        assertEquals(expected, actual);
    }

    @Test
    public void joinQueryBuilderWithExpression() {
        QueryBuilder inner1 = Squel.select().from("students");
        QueryBuilder inner2 = Squel.select().from(inner1);
        Expression exp = Squel.expr().and("meh.ID = ID");

        String actual = Squel.select()
                .from("schools")
                .join(inner2, "meh", exp)
                .toString();

        String expected = "SELECT * FROM schools INNER JOIN (SELECT * FROM (SELECT * FROM students)) `meh` ON (meh.ID = ID)";
        assertEquals(expected, actual);
    }

    @Test
    public void unionTables() {
        QueryBuilder sql = Squel.select()
                .from("schools")
                .union("universities", UnionType.UNION);
        String actual = sql.toString();
        String expected = "SELECT * FROM schools UNION universities";
        assertEquals(expected, actual);
    }

    @Test
    public void unionAllExplicit() {
        QueryBuilder sql = Squel.select()
                .from("schools")
                .union("universities", UnionType.UNION_ALL);
        String actual = sql.toString();
        String expected = "SELECT * FROM schools UNION ALL universities";
        assertEquals(expected, actual);
    }

    @Test
    public void unionAll() {
        QueryBuilder sql = Squel.select()
                .from("schools")
                .unionAll("universities");
        String actual = sql.toString();
        String expected = "SELECT * FROM schools UNION ALL universities";
        assertEquals(expected, actual);
    }

    @Test
    public void unionTwoQueries() {
        QueryBuilder q1 = Squel.select().field("name").from("students").where("age > 15");
        QueryBuilder q2 = Squel.select().field("name").from("students").where("age < 6");

        String actual = q1.union(q2).toString();
        String expected = "SELECT name FROM students WHERE (age > 15) UNION (SELECT name FROM students WHERE (age < 6))";
        assertEquals(expected, actual);
    }

    @Test
    public void unionThreeQueries() {
        QueryBuilder q1 = Squel.select().field("name").from("students").where("age > 15");
        QueryBuilder q2 = Squel.select().field("name").from("students").where("age < 6");
        QueryBuilder q3 = Squel.select().field("name").from("students").where("age = 8");

        String actual = q1.union(q2).union(q3).toString();
        String expected = "SELECT name FROM students WHERE (age > 15) UNION (SELECT name FROM students WHERE (age < 6)) UNION (SELECT name FROM students WHERE (age = 8))";
        assertEquals(expected, actual);
    }

    //
    // INVALID USAGE
    //

    @Test(expected=UnsupportedOperationException.class)
    public void setShouldThrow() {
        Squel.select()
                .set("NAME", "VALUE");
    }
}
