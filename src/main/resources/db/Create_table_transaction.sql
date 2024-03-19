CREATE TABLE IF NOT EXISTS transaction(
    id SERIAL PRIMARY KEY,
    amount DOUBLE PRECISION,
    lable TEXT,
    dateEffect TIMESTAMP,
    saveDate TIMESTAMP,
    extern BOOLEAN,
    status BOOLEAN,
    id_account_sender INT REFERENCES account(id),
    id_account_recipient INT REFERENCES account(id),
    id_category INT REFERENCES category(id)
);