CREATE TABLE IF NOT EXISTS currency
(
    id     INTEGER PRIMARY KEY,
    ticker TEXT    NOT NULL,
    name   TEXT    DEFAULT ''
);
