package com.github.gchudnov.samples.usage;

import com.github.gchudnov.squel.JoinType;
import com.github.gchudnov.squel.QueryBuilder;
import com.github.gchudnov.squel.Squel;

public class Program {
    public static void main(String[] args) {

//        {
//            QueryBuilder q = Squel.select()
//                    .from("students")
//                    .field("name")
//                    .field("MIN(test_score)")
//                    .field("MAX(test_score)")
//                    .field("GROUP_CONCAT(DISTINCT test_score ORDER BY test_score DESC SEPARATOR ' ')")
//                    .group("name");
//
//            System.out.println(q);
//
//            /*
//            SELECT name, MIN(test_score), MAX(test_score), GROUP_CONCAT(DISTINCT test_score ORDER BY test_score DESC SEPARATOR ' ') FROM students GROUP BY name
//             */
//        }
//
//        {
//            QueryBuilder q = Squel.select()
//                    .from("users");
//
//            System.out.println(q);
//
//            /*
//            SELECT * FROM students
//            */
//        }
//
//        {
//            QueryBuilder q = Squel.select()
//                    .from("users")
//                    .from("groups", "g")
//                    .from("admins", "a");
//
//            System.out.println(q);
//
//            /*
//            SELECT * FROM users, groups `g`, admins `a`
//            */
//        }
//
//        {
//            QueryBuilder q = Squel.select()
//                    .from(Squel.select().from("users"), "u")
//                    .field("u.id");
//
//            System.out.println(q);
//
//            /*
//            SELECT u.id FROM (SELECT * FROM users) `u`
//            */
//        }
//
//        {
//            QueryBuilder q = Squel.select()
//                    .from("users")
//                    .field("first_name")
//                    .field("last_name", "Last Name");
//
//            System.out.println(q);
//
//            /*
//            SELECT first_name, last_name AS "Last Name" FROM users
//            */
//        }

//        {
//            QueryBuilder q = Squel.select()
//                    .from("users")
//                    .field("login")
//                    .field(Squel.select().from("audit").field("COUNT(*)"), "records");
//
//            System.out.println(q);
//
//            /*
//            SELECT login, (SELECT COUNT(*) FROM audit) AS "records" FROM users
//            */
//        }

        {
            QueryBuilder q = Squel.select()
                    .from("users")
                    .distinct();

            System.out.println(q);

            /*
            SELECT DISTINCT * FROM users
            */
        }

        {
            QueryBuilder q = Squel.select()
                    .from("users", "u")
                    .join("groups", "g")
                    .join("supergroups", "sg", "sg.id = u.id", JoinType.LEFT)
                    .rightJoin("rights")
                    .leftJoin("bonuses")
                    .distinct();

            System.out.println(q);

            /*
            SELECT DISTINCT * FROM users `u` INNER JOIN groups `g` LEFT JOIN supergroups `sg` ON (sg.id = u.id)
            */
        }


    }
}