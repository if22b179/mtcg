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

CREATE TABLE CardTable (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    damage DECIMAL NOT NULL,
    element_type VARCHAR(50) NOT NULL CHECK (element_type IN ('FIRE', 'WATER', 'NORMAL')),
    card_type VARCHAR(50) NOT NULL CHECK (card_type IN ('SPELL', 'MONSTER')),
    owner_username VARCHAR(255) REFERENCES usertable(username), -- Verweis auf die 'usertable'
    package_id VARCHAR(255), -- Optional, falls es eine 'packages'-Tabelle gibt
    in_deck BOOLEAN NOT NULL DEFAULT false
);

DROP TABLE CardTable;