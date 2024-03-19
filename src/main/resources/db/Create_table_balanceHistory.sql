CREATE TABLE IF NOT EXISTS balanceHistory(
    id UUID PRIMARY KEY,
    balance DOUBLE PRECISION,
    dateTime TIMESTAMP,
    id_account INT REFERENCES account(id)
);