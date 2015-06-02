package com.github.gchudnov.squel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test Selection
 */
public class SelectTest {

    @Test
    public void selectQuery() {
        String actual = Squel.select()
                .from("students")
                .field("name")
                .field("MIN(test_score)")
                .field("MAX(test_score)")
                .field("GROUP_CONCAT(DISTINCT test_score ORDER BY test_score DESC SEPARATOR ' ')")
                .group("name")
                .toString();

        String expected = "";

        assertEquals(expected, actual);
    }

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