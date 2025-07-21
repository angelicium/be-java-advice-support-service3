--liquibase formatted sql
--changeset Korkin Sergey:2025-05-28-create-table-user
CREATE TABLE IF NOT EXISTS "user"
(
    id    UUID         PRIMARY KEY NOT NULL,
    name  VARCHAR(255)             NOT NULL,
    email VARCHAR(100)             NOT NULL
    );