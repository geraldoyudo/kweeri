# kweeri

Library for generating generic queries

## Description

Have you ever wanted to be able to query your resources from a rest api using 
a simple, customizable syntax? Like this

````
get users where username equals 'user' and nationality equals 'Nigerian'
````

This simple framework provides the ability to parse queries like this into 
any database query. You can have queries like this converted to sql, or mongo, 
or any database of your choice. 

This helpes us decouple our query system from our persistence layer and enables us 
to switch easily by changing converters.

### What Converters do we presently have?

We currently have a simple converter for mongo. It converts queries like that above 
into mongo json queries that can be used by any mongo client.

### What are future plans

To have converters for all databases

- SQL
- Cassandra
- other databases. 

### Can you use this to make yours?

The library is easily customizable, just take a look at the code and how 
the mongo converter was done. 

## Getting Started

### Using Kweeri Core

````
<dependencies>
    <dependency>
        <groupId>com.geraldoyudo.kweeri</groupId>
        <artifactId>kweeri-core</artifactId>
        <version>0.0.1</version>
    </dependency>
</dependencies>
````

### Using the converters

#### Mongo DB

````
<dependencies>
    <dependency>
        <groupId>com.geraldoyudo.kweeri</groupId>
        <artifactId>kweeri-mongo</artifactId>
        <version>0.0.1</version>
    </dependency>
</dependencies>
````

