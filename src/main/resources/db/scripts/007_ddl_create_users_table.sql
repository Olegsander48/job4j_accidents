CREATE TABLE users (
    username VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    enabled  BOOLEAN DEFAULT TRUE,
    PRIMARY KEY (username)
);