# squel-java
A Java-port of [Squel.js](https://hiddentao.github.io/squel/) - a lightweight library for building SQL query strings

## Requirements

`squel-java` can be included in any Java or Android application.


## Installation

If you are building with Gradle, simply add the following line to the `dependencies` section of your `build.gradle` file:

```groovy
compile 'com.github.gchudnov.squel:squel:0.9.0+'
```

## Oveview
TBD

## API
To use the library, call one of the static method for the Squel class: `.select()`, `.update()`, `.insert()` or `.delete()`. It starts a new `QueryBuilder` chain you can attach next method invocations to.

To get a resulting SQL, call `toString` on `QueryBuilder`.

### SELECT
To create a SELECT-query, get a SELECT Query builder by invoking `Squel.select()` and provide the name of a table to `from` method:
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

### UPDATE

### DELETE

### INSERT

### PARAMETERS

### EPRESSIONS


## Contact

[Grigoriy Chudnov] (mailto:g.chudnov@gmail.com)


## License

Distributed under the [The MIT License (MIT)](https://github.com/gchudnov/bspec/blob/master/LICENSE).
