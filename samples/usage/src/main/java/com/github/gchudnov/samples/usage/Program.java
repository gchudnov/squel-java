package com.github.gchudnov.samples.usage;

import com.github.gchudnov.squel.QueryBuilder;
import com.github.gchudnov.squel.Squel;

public class Program
{
    public static void main(String[] args)
    {
        {
            QueryBuilder q = Squel.select()
                    .from("students")
                    .field("name")
                    .field("MIN(test_score)")
                    .field("MAX(test_score)")
                    .field("GROUP_CONCAT(DISTINCT test_score ORDER BY test_score DESC SEPARATOR ' ')")
                    .group("name");

            System.out.println(q);

            /*
            SELECT name, MIN(test_score), MAX(test_score), GROUP_CONCAT(DISTINCT test_score ORDER BY test_score DESC SEPARATOR ' ') FROM students GROUP BY name
             */
        }

        {
            QueryBuilder q = Squel.select()
                    .from("students");

            System.out.println(q);

            /*
            SELECT * FROM students
             */
        }

    }
}