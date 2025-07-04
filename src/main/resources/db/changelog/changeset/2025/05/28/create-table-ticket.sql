--liquibase formatted sql
--changeset Simonyan-Angelica 2025-05-28:create-table-ticket

CREATE TABLE IF NOT EXISTS ticket(
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       user_id UUID NOT NULL,
                       category_id INTEGER NOT NULL,
                       priority_id INTEGER NOT NULL,
                       status_id INTEGER NOT NULL,
                       title VARCHAR(255) NOT NULL,
                       description TEXT NOT NULL,
                       sla_deadline TIMESTAMP,
                       escalated_at TIMESTAMP,
                       created_at TIMESTAMP NOT NULL DEFAULT now(),
                       updated_at timestamp,
                       closed_at timestamp,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (category_id) REFERENCES ticket_category (id),
    FOREIGN KEY (priority_id) REFERENCES ticket_priority (id),
    FOREIGN KEY (status_id) REFERENCES ticket_status (id)
);