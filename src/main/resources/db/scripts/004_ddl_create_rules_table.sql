CREATE TABLE rules (
    id          serial PRIMARY KEY,
    name        varchar NOT NULL,
    accident_id int REFERENCES accidents(id)
);