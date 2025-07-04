--liquibase formatted sql
--changeset Simonyan-Angelica 2025-05-28:create-table-ticket-priority

CREATE TABLE IF NOT EXISTS ticket_priority
(
id INTEGER PRIMARY KEY NOT NULL,
name VARCHAR(100) NOT NULL,
description TEXT
    );
INSERT INTO ticket_priority(id, name, description)
VALUES
  (1, 'LOW', 'Низкий приоритет, может быть отложен'),
  (2, 'MEDIUM', 'Средний приоритет, требует внимания'),
  (3, 'HIGH', 'Высокий приоритет, требует немедленного внимания')
ON CONFLICT (id) DO NOTHING;
