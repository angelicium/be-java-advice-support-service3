--liquibase formatted sql
--changeset Evseev Lev 2025-07-13:create-table-attachment

CREATE TABLE IF NOT EXISTS attachment(
    id          UUID    PRIMARY KEY,
    ticket_id   UUID    NOT NULL,
    file_id     UUID    NOT NULL,
    uploaded_at TIMESTAMP NOT NULL DEFAULT now(),
    FOREIGN KEY (ticket_id) REFERENCES ticket (id)
);