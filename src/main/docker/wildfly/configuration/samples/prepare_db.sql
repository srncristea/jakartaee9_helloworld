

CREATE TABLE User (
    id BIGSERIAL NOT NULL,
    identifier uuid DEFAULT uuid_generate_v4 (),
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

insert into user(id, firstname, lastname, password, password, email, phone)
values (100, 'sorin','cristea', 'sorin.sorin', 'sorin.sorin', 'sorin.sorin@ekghome.com', '0753985451');

insert into user(id, firstName, lastName, userName, password, email, phone)
values (101, 'sorin','cristea', 'sorin.sorin', 'sorin.sorin', 'sorin.sorin@ekghome.com', '0753985451');