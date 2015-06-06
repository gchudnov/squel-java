package com.github.gchudnov.samples.usage;

import com.github.gchudnov.squel.*;

import java.util.Arrays;

public class Program {
    public static void main(String[] args) {

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
                    .from("users");

            System.out.println(q);

            /*
            SELECT * FROM students
            */
        }

        {
            QueryBuilder q = Squel.select()
                    .from("users")
                    .from("groups", "g")
                    .from("admins", "a");

            System.out.println(q);

            /*
            SELECT * FROM users, groups `g`, admins `a`
            */
        }

        {
            QueryBuilder q = Squel.select()
                    .from(Squel.select().from("users"), "u")
                    .field("u.id");

            System.out.println(q);

            /*
            SELECT u.id FROM (SELECT * FROM users) `u`
            */
        }

        {
            QueryBuilder q = Squel.select()
                    .from("users")
                    .field("first_name")
                    .field("last_name", "Last Name");

            System.out.println(q);

            /*
            SELECT first_name, last_name AS "Last Name" FROM users
            */
        }

        {
            QueryBuilder q = Squel.select()
                    .from("users")
                    .field("login")
                    .field(Squel.select().from("audit").field("COUNT(*)"), "records");

            System.out.println(q);

            /*
            SELECT login, (SELECT COUNT(*) FROM audit) AS "records" FROM users
            */
        }

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
                    .join("extras", "sg", "ex.id = u.id", JoinType.LEFT)
                    .rightJoin("rights")
                    .leftJoin("bonuses")
                    .distinct();

            System.out.println(q);

            /*
            SELECT DISTINCT * FROM users `u` INNER JOIN groups `g` LEFT JOIN extras `sg` ON (ex.id = u.id) RIGHT JOIN rights LEFT JOIN bonuses
            */
        }

        {
            QueryBuilder q = Squel.select()
                    .from("users")
                    .where("id = 10");

            System.out.println(q);

            /*
            SELECT * FROM users WHERE (id = 10)
            */
        }

        {
            QueryBuilder q = Squel.select()
                    .from("users")
                    .where("name = ?", "Bob");

            System.out.println(q);

            /*
            SELECT * FROM users WHERE (name = 'Bob')
            */
        }

        {
            QueryBuilder q = Squel.select()
                    .from("users")
                    .where("name = ?", "Bob")
                    .where("age > ?", 45);

            System.out.println(q);

            /*
            SELECT * FROM users WHERE (name = 'Bob') AND (age > 45)
            */
        }

        {
            QueryBuilder q = Squel.select()
                    .from("users")
                    .where(Squel.expr().and("name = ?", "Bob").or("age < ?", 45));

            System.out.println(q);

            /*
            SELECT * FROM users WHERE (name = 'Bob' OR age < 45)
            */
        }

        {
            QueryBuilder q = Squel.select()
                    .from("users")
                    .order("id")
                    .order("name", SortOrder.DESC);

            System.out.println(q);

            /*
            SELECT * FROM users ORDER BY id ASC, name DESC
            */
        }

        {
            QueryBuilder q = Squel.select()
                    .from("users")
                    .group("age");

            System.out.println(q);

            /*
            SELECT * FROM users GROUP BY age
            */
        }

        {
            QueryBuilder q = Squel.select()
                    .from("users")
                    .limit(10)
                    .offset(3);

            System.out.println(q);

            /*
            SELECT * FROM users LIMIT 10 OFFSET 3
            */
        }

        {
            QueryBuilder q = Squel.update()
                    .table("users")
                    .set("name", "Bob");

            System.out.println(q);

            /*
            UPDATE users SET name = 'Bob'
            */
        }

        {
            QueryBuilder q = Squel.update()
                    .table("users")
                    .set("name", "Bob")
                    .set("occupation", "Builder")
                    .where("age > ?", 30);

            System.out.println(q);

            /*
            UPDATE users SET name = 'Bob', occupation = 'Builder' WHERE (age > 30)
            */
        }

        {
            QueryBuilder q = Squel.delete()
                    .from("users");

            System.out.println(q);

            /*
            DELETE FROM users
            */
        }

        {
            QueryBuilder q = Squel.delete()
                    .from("users")
                    .where("id > 10");

            System.out.println(q);

            /*
            DELETE FROM users WHERE (id > 10)
            */
        }

        {
            QueryBuilder q = Squel.insert()
                    .into("users")
                    .fromQuery(Arrays.asList("name", "surname"), Squel.select().from("people").field("n").field("s"));

            System.out.println(q);

            /*
            INSERT INTO users (name, surname) (SELECT n, s FROM people)
            */
        }

        {
            Expression e = Squel.expr()
                    .and("s.guid = ?", "77627a69")
                    .andBegin()
                        .or("s.id = 5")
                        .or("s.id = 6")
                    .end()
                    .orBegin()
                        .and("sid = 1")
                        .and("pid = 1")
                    .end();

            System.out.println(e);

            /*
            s.guid = '77627a69' AND (s.id = 5 OR s.id = 6) OR (sid = 1 AND pid = 1)
            */
        }

        {
            QueryBuilder q = Squel.select()
                    .from("users")
                    .where(Squel.expr().and("role = ?", "admin").and("id > 12"));

            System.out.println(q);

            /*
            SELECT * FROM users WHERE (role = 'admin' AND id > 12)
            */
        }
    }
}
