CREATE TYPE issue_status AS ENUM ('new', 'open', 'closed');

CREATE TABLE issue_tracker
(
    id          serial,
    description text,
    status      issue_status,
    created_on  DATE
);