CREATE DATABASE mtcgDb;


CREATE TABLE UserTable (
    username VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    security_token VARCHAR(255) default null,
    name VARCHAR(255) default null,
    bio TEXT default null,
    image TEXT default null,
    elo_value INTEGER DEFAULT 100,
    virtual_coins INTEGER DEFAULT 20
);

DROP TABLE users;