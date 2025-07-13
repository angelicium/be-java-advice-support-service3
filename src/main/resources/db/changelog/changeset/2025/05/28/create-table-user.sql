--liquibase formatted sql
--changeset Korkin Sergey:2025-05-28-create-table-user
CREATE TABLE IF NOT EXISTS "user"
(
    id    UUID         PRIMARY KEY NOT NULL,
    name  VARCHAR(255)             NOT NULL,
    email VARCHAR(100)             NOT NULL
    );
INSERT INTO "user" (id, name, email)
VALUES
    ('123e4567-e89b-12d3-a456-426614174000', 'NEW', 'Новый тикет, ожидает назначения')
    ON CONFLICT (id) DO NOTHING;