-- INIT DATABASE WITH CREATE TABLE
-- ACCOUNT
CREATE TABLE IF NOT EXISTS account(
    id SERIAL PRIMARY KEY,
    customerFirstName VARCHAR(200),
    customerLastName VARCHAR(200),
    birthdate DATE,
    netSalary DOUBLE PRECISION,
    number VARCHAR(10) UNIQUE,
    debt BOOLEAN
);

-- BALANCEHISTORY
CREATE TABLE IF NOT EXISTS balanceHistory(
    id SERIAL PRIMARY KEY,
    balance DOUBLE PRECISION,
    dateTime TIMESTAMP,
    id_account INT REFERENCES account(id)
);

-- CATEGORY
CREATE TABLE IF NOT EXISTS category(
    id SERIAL PRIMARY KEY,
    name VARCHAR(200),
    type VARCHAR(200)
);

-- TRANSACTION
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
