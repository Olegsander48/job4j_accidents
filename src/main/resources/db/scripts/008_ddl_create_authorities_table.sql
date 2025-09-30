CREATE TABLE authorities (
    username  VARCHAR NOT NULL,
    authority VARCHAR NOT NULL,
    FOREIGN KEY (username) REFERENCES users (username)
);