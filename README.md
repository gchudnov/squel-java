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
To use the library, call one of the static method of the Squel class: `.select()`, `.update()`, `.insert()` or `.delete()`. Calling one of this starts a new `QueryBuilder` chain.

To create an `Expression`, invoke: `.expr()`.

To get a resulting SQL, call `toString` on `QueryBuilder` or `Expression`.

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

## UPDATE

## DELETE

## INSERT

## PARAMETERS

## EPRESSIONS


## Contact

[Grigoriy Chudnov] (mailto:g.chudnov@gmail.com)


## License

Distributed under the [The MIT License (MIT)](https://github.com/gchudnov/bspec/blob/master/LICENSE).
