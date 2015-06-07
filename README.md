# squel-java
A lightweight library for building SQL query string through a fluent API.
A Java-port of [Squel.js](https://hiddentao.github.io/squel/).

## Requirements

`squel-java` can be included in any Java or Android application.


## Installation

Add the following line to the `dependencies` section of your `build.gradle` file:

```groovy
compile 'com.github.gchudnov.squel:squel:0.9.0'
```

## API
To use the library, call one of the static method from the `Squel` class: `.select()`, `.update()`, `.insert()` or `.delete()`. It starts a new `QueryBuilder` chain you can attach next method invocations to.

To get a resulting SQL, call `toString` on `QueryBuilder`.

### SELECT
To create a SELECT-query, get a QueryBuilder instance by invoking `Squel.select()` and provide the name of a table:
```java
QueryBuilder q = Squel.select()
    .from("users");
/* SELECT * FROM users */    
```

#### Multiple tables
You can select data from multiple tables and provide aliases where needed:
```java
QueryBuilder q = Squel.select()
    .from("users")
    .from("groups", "g")
    .from("admins", "a");
/* SELECT * FROM users, groups `g`, admins `a` */    
```

#### Sub-queries as tables
```Java
QueryBuilder q = Squel.select()
    .from(Squel.select().from("users"), "u")
    .field("u.id");
/* SELECT u.id FROM (SELECT * FROM users) `u` */
```

#### Fields
Specify field names and optionally aliases:
```java
QueryBuilder q = Squel.select()
    .from("users")
    .field("first_name")
    .field("last_name", "Last Name");
/* SELECT first_name, last_name AS "Last Name" FROM users */
```
To create more complex queries, use a `QueryBuilder` instead of the field name:
```java
QueryBuilder q = Squel.select()
    .from("users")
    .field("login")
    .field(Squel.select().from("audit").field("COUNT(*)"), "records");
/* SELECT login, (SELECT COUNT(*) FROM audit) AS "records" FROM users */
```

#### Distinct
Obtaining distinct results:
```java
QueryBuilder q = Squel.select()
    .from("users")
    .distinct();
/* SELECT DISTINCT * FROM users */
```

#### Joins
Five types of JOIN operations are available: `INNER`, `LEFT`, `RIGHT`, `FULL` and `CROSS`. Use `.join()`, `.innerJoin()`, `.leftJoin()`, `.rightJoin()`, `.fullJoin()` and `.crossJoin()` to define a join operation.
```java
QueryBuilder q = Squel.select()
    .from("users", "u")
    .join("groups", "g")
    .join("extras", "sg", "ex.id = u.id", JoinType.LEFT)
    .rightJoin("rights")
    .leftJoin("bonuses")
    .distinct();
/* SELECT DISTINCT * FROM users `u` INNER JOIN groups `g` LEFT JOIN extras `sg` ON (ex.id = u.id) RIGHT JOIN rights LEFT JOIN bonuses */
```

#### Filering
Use `.where()` to add WHERE-clause to the query:
```java
QueryBuilder q = Squel.select()
    .from("users")
    .where("id = 10");
/* SELECT * FROM users WHERE (id = 10) */
```

Using parameter substitution:
```java
QueryBuilder q = Squel.select()
    .from("users")
    .where("name = ?", "Bob");
/* SELECT * FROM users WHERE (name = 'Bob') */
```

Multiple `.where()` calls are combined with `AND` automatically:
```java
QueryBuilder q = Squel.select()
    .from("users")
    .where("name = ?", "Bob")
    .where("age > ?", 45);
/* SELECT * FROM users WHERE (name = 'Bob') AND (age > 45) */
```

Comples filtering can be implemented using expressions:
```java
QueryBuilder q = Squel.select()
    .from("users")
    .where(Squel.expr().and("name = ?", "Bob").or("age < ?", 45));
/* SELECT * FROM users WHERE (name = 'Bob' OR age < 45) */
```

#### Sorting
Call `.order()` to specify the sorting order:
```java
QueryBuilder q = Squel.select()
    .from("users")
    .order("id")
    .order("name", SortOrder.DESC);
/* SELECT * FROM users ORDER BY id ASC, name DESC */
```

#### Grouping
To group the records in the resultset, call `.group()`:
```java
QueryBuilder q = Squel.select()
    .from("users")
    .group("age");
/* SELECT * FROM users GROUP BY age */
```

#### Limit and Offset
```java
QueryBuilder q = Squel.select()
    .from("users")
    .limit(10)
    .offset(3);
/* SELECT * FROM users LIMIT 10 OFFSET 3 */
```

### UPDATE
To create an UPDATE-query, get a QueryBuilder instance by invoking `Squel.update()`, provide the name of a table and a field to update:
```java
QueryBuilder q = Squel.update()
    .table("users")
    .set("name", "Bob");
/* UPDATE users SET name = 'Bob' */  
```

To filter the updated rows, use `.where()`:
```java
QueryBuilder q = Squel.update()
    .table("users")
    .set("name", "Bob")
    .set("occupation", "Builder")
    .where("age > ?", 30);
/* UPDATE users SET name = 'Bob', occupation = 'Builder' WHERE (age > 30) */
```

### DELETE
To create a DELETE-statement, get a QueryBuilder instance by invoking `Squel.delete()` and provide the name of a table:
```java
QueryBuilder q = Squel.delete()
    .from("users");
/* DELETE FROM users */
```

To filter the removed rows, use `.where()`:
```java
QueryBuilder q = Squel.delete()
    .from("users")
    .where("id > 10");
/* DELETE FROM users WHERE (id > 10) */
```
### INSERT
To create an INSERT-statement, get a QueryBuilder instance by invoking `Squel.insert()` and provide the name of a table and at least one field:
```java
QueryBuilder q = Squel.insert()
    .into("users")
    .set("name", "Bob")
    .set("occupation", "Builder");
/* INSERT INTO users (name, occupation) VALUES ('Bob', 'Builder') */
```

To insert from selection, use '.fromQuery()' method:
```java
QueryBuilder q = Squel.insert()
    .into("users")
    .fromQuery(Arrays.asList("name", "surname"), Squel.select().from("people").field("n").field("s"));
/* INSERT INTO users (name, surname) (SELECT n, s FROM people) */
```

### Expressions
To create a complex expression, call `Squel.expr()` method to initiate the expression builder, `Expression`.
```java
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
/* s.guid = '77627a69' AND (s.id = 5 OR s.id = 6) OR (sid = 1 AND pid = 1) */
```

Expression instances can be used with `.where()` and `.join()` methods:
```java
QueryBuilder q = Squel.select()
    .from("users")
    .where(Squel.expr().and("role = ?", "admin").and("id > 12"));
/* SELECT * FROM users WHERE (role = 'admin' AND id > 12) */
```


## Contact

[Grigoriy Chudnov] (mailto:g.chudnov@gmail.com)


## License

Distributed under the [The MIT License (MIT)](https://github.com/gchudnov/squel-java/blob/master/LICENSE).
