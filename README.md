# squel-java
A Java-port of [Squel.js](https://hiddentao.github.io/squel/) - a lightweight library for building SQL query strings

## Requirements

`squel-java` can be included in any Java or Android application.

`squel-java` supports Android 2.3 (Gingerbread) and later. 
 

## Installation

If you are building with Gradle, simply add the following line to the `dependencies` section of your `build.gradle` file:

```groovy
compile 'com.github.gchudnov:squel:0.9.0+'
```

## Oveview
TBD

## SELECT

To get an instance of the `SELECT` query builder:
```java
        QueryBuilder q = Squel.select()
                .from("students");
/*
SELECT * FROM students
*/
```

## Contact

[Grigoriy Chudnov] (mailto:g.chudnov@gmail.com)


## License

Distributed under the [The MIT License (MIT)](https://github.com/gchudnov/bspec/blob/master/LICENSE).
