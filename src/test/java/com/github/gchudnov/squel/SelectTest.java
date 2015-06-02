package com.github.gchudnov.squel;

import org.junit.Test;

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
    public void complexQuery() {
        String actual = Squel.select()
                .from("table")
                .from("table2", "alias2")
                .field(Squel.select().field("MAX(score)").from("scores"), "fa1")
                .toString();
        String expected = "SELECT (SELECT MAX(score) FROM scores) AS \"fa1\" FROM table, table2 `alias2`";
        assertEquals(expected, actual);
    }




    /*
      '>> field(squel.select().field("MAX(score)").FROM("scores"), fa1)':
        beforeEach: -> @inst.field(squel.select().field("MAX(score)").from("scores"), 'fa1')
        toString: ->
          assert.same @inst.toString(), 'SELECT (SELECT MAX(score) FROM scores) AS "fa1" FROM table, table2 `alias2`'

      '>> field(field1, fa1) >> field(field2)':
        beforeEach: -> @inst.field('field1', 'fa1').field('field2')
        toString: ->
          assert.same @inst.toString(), 'SELECT field1 AS "fa1", field2 FROM table, table2 `alias2`'


     */

//    @Test
//    public void selectQuery() {
//        String actual = Squel.select()
//                .from("students")
//                .field("name")
//                .field("MIN(test_score)")
//                .field("MAX(test_score)")
//                .field("GROUP_CONCAT(DISTINCT test_score ORDER BY test_score DESC SEPARATOR ' ')")
//                .group("name")
//                .toString();
//
//        String expected = "";
//
//        assertEquals(expected, actual);
//    }

}

/*
//    @Test
//    public void selectQuery() {
//        String actual = Squel.select()
//                .from("students")
//                .field("name")
//                .field("MIN(test_score)")
//                .field("MAX(test_score)")
//                .field("GROUP_CONCAT(DISTINCT test_score ORDER BY test_score DESC SEPARATOR ' ')")
//                .group("name")
//                .toString();
//
//        String expected = "";
//
//        Assert.assertEquals(expected, actual);
//    }

//    @Test
//    public void advancedSelectQuery() {
//        String actual = Squel.select()
//                .from("product", "p")
//                .field("_id")
//                .join("field", "f", "f.product = p._id")
//                .where("p.report = %s")
////                .field("MIN(test_score)")
////                .field("MAX(test_score)")
////                .field("GROUP_CONCAT(DISTINCT test_score ORDER BY test_score DESC SEPARATOR ' ')")
////                .group("name")
//                .toString();
//
//        String expected = "";
//
//        Assert.assertEquals(expected, actual);
//    }

//    @Test
//    public void expressionTest() {
//        String actual = Squel.expr()
//                .and("id < 500")
//                .and_begin()
//                    .or("id > 100")
//                    .or("name <> 'Thomas'")
//                .end()
//                .and_begin()
//                    .or("age BETWEEN 20 AND 25")
//                    .or("name <> 'Fred'")
//                .end()
//                .or("nickname = 'Hardy'")
//        .toString();
//
//        String expected = "";
//        Assert.assertEquals(expected, actual);
//    }


 */