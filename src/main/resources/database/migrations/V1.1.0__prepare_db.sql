
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE SCHEMA IF NOT EXISTS mso_ekg;
DROP TABLE IF EXISTS mso_ekg.User CASCADE;

CREATE TABLE mso_ekg.User (
    id BIGSERIAL NOT NULL,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL,

    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
    updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    version BIGSERIAL,

    CONSTRAINT user_pk PRIMARY KEY (id),
    CONSTRAINT user_unique_email UNIQUE (email)
);


DROP SEQUENCE IF EXISTS mso_ekg.user_identifier_seq;
CREATE SEQUENCE mso_ekg.user_identifier_seq INCREMENT 1;


INSERT INTO mso_ekg.User(id, firstname, lastname, username, password, email, phone, version)
VALUES (10001, 'sorin','cristea', 'sorin.sorin', 'sorin.sorin', 'sorin.sorin@ekghome.com', '0753985451', 0);

INSERT INTO mso_ekg.User(id, firstName, lastName, username, password, email, phone, version)
VALUES (10002, 'sorin','lazar', 'sorin.lazar', 'sorin.lazar', 'sorin.lazar@ekghome.com', '0753985451', 0);