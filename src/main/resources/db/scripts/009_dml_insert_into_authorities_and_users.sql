INSERT INTO authorities (authority) VALUES ('ROLE_USER');
INSERT INTO authorities (authority) VALUES ('ROLE_ADMIN');

INSERT INTO users (username, enabled, password, authority_id)
VALUES ('root', true, '$2a$10$03aoyfpXJW6W2FUKpfmIeOeRnslkTGvC6VOhlJJQO31ZPQqBr.8K2',
        (SELECT id FROM authorities WHERE authority = 'ROLE_ADMIN'));